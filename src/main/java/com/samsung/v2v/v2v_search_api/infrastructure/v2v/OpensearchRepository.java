package com.samsung.v2v.v2v_search_api.infrastructure.v2v;

import com.samsung.v2v.v2v_search_api.domain.v2v.V2VContent;
import org.opensearch.action.admin.indices.delete.DeleteIndexRequest;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.support.master.AcknowledgedResponse;
import org.opensearch.client.indices.PutIndexTemplateRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface OpensearchRepository {


    boolean isExistsTemplate(String templateName) throws IOException;

    boolean isExistsIndex(String indexName) throws IOException;

    AcknowledgedResponse putIndexTemplate(PutIndexTemplateRequest putIndexTemplateRequest) throws IOException;

    AcknowledgedResponse deleteIndex(DeleteIndexRequest deleteIndexRequest) throws IOException;

    Optional<List<V2VContent>> findAllBySearchCommand(SearchRequest searchRequest);

}


