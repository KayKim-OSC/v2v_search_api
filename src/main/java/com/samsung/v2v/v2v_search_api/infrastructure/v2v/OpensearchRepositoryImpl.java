package com.samsung.v2v.v2v_search_api.infrastructure.v2v;

import com.samsung.v2v.v2v_search_api.common.constants.QueryOption;
import com.samsung.v2v.v2v_search_api.common.exception.NetworkException;
import com.samsung.v2v.v2v_search_api.common.util.json.JsonConverter;
import com.samsung.v2v.v2v_search_api.domain.v2v.V2VContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.OpenSearchException;
import org.opensearch.OpenSearchStatusException;
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.action.support.master.AcknowledgedResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.client.indices.IndexTemplatesExistRequest;
import org.opensearch.client.indices.PutIndexTemplateRequest;
import org.opensearch.search.SearchHit;
import org.opensearch.search.aggregations.metrics.Cardinality;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OpensearchRepositoryImpl implements OpensearchRepository {

    private final RestHighLevelClient openSearchClient;

    private final JsonConverter jsonConverter;

    @Override
    public boolean isExistsTemplate(String templateName) throws IOException {
        IndexTemplatesExistRequest indexTemplatesExistRequest = new IndexTemplatesExistRequest(templateName);
        return openSearchClient.indices().existsTemplate(indexTemplatesExistRequest, RequestOptions.DEFAULT);
    }

    @Override
    public boolean isExistsIndex(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        return openSearchClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

    }

    @Override
    public AcknowledgedResponse putIndexTemplate(PutIndexTemplateRequest putIndexTemplateRequest) throws IOException {
        return openSearchClient.indices().putTemplate(putIndexTemplateRequest, RequestOptions.DEFAULT);
    }

    @Override
    public AcknowledgedResponse deleteIndex(DeleteIndexRequest deleteIndexRequest) throws IOException {
        return openSearchClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
    }

    @Override
    public Optional<List<V2VContent>> findAllBySearchCommand(SearchRequest searchRequest) {
        List<V2VContent> result = new ArrayList<>();
        SearchResponse searchResponse = null;
        try {
            searchResponse = openSearchClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            if (searchHits.length == 0) {
                return Optional.empty();
            }
            /*검색 결과 데이터 셋*/
            for (SearchHit searchHit : searchHits) {
                V2VContent v2VContent = jsonConverter.toV2vContent(searchHit.getSourceAsMap());

                if (searchResponse.getAggregations() != null) {
                    Cardinality totalCount = searchResponse.getAggregations().get(QueryOption.TOTAL_COUNT_AGGREGATION.getValue());
                    v2VContent.setTotalCount(totalCount.getValue() + 1);
                } else {
                    v2VContent.setTotalCount(searchResponse.getHits().getTotalHits().value);
                }

                //duplicateYn이 N 인 경우
                if (searchHit.getInnerHits() == null) {
                    v2VContent.setDupicateCount(null);
                }else {
                    if (v2VContent.getReplaceTitle() == null) {
                        v2VContent.setDupicateCount(0L);
                    }else{
                        v2VContent.setDupicateCount(searchHit.getInnerHits().get(QueryOption.SAME_REPLACE_TITLE_COUNT.getValue()).getTotalHits().value);
                    }
                }

                result.add(v2VContent);
            }
            return Optional.of(result);
        } catch (OpenSearchStatusException e) {
            throw new OpenSearchException(e);
        } catch (IOException e) {
            throw new NetworkException(e.getMessage());
        }
    }

}
