package com.samsung.v2v.v2v_search_api.infrastructure.v2v;

import com.samsung.v2v.v2v_search_api.common.constants.Criteria;
import com.samsung.v2v.v2v_search_api.common.constants.LanguageCode;
import com.samsung.v2v.v2v_search_api.common.constants.QueryKey;
import com.samsung.v2v.v2v_search_api.common.constants.QueryOption;
import com.samsung.v2v.v2v_search_api.common.exception.DocumentNotFoundException;
import com.samsung.v2v.v2v_search_api.common.util.ConverterUtil;
import com.samsung.v2v.v2v_search_api.domain.v2v.DocumentCommand;
import com.samsung.v2v.v2v_search_api.domain.v2v.OpensearchReader;
import com.samsung.v2v.v2v_search_api.domain.v2v.V2VContent;
import com.samsung.v2v.v2v_search_api.interfaces.v2v.SearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.common.unit.Fuzziness;
import org.opensearch.common.unit.TimeValue;
import org.opensearch.index.query.*;
import org.opensearch.search.aggregations.AggregationBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.opensearch.search.collapse.CollapseBuilder;
import org.opensearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpensearchReaderImpl implements OpensearchReader {

    private final OpensearchRepository opensearchRepository;

    @Override
    public boolean hasTemplate(String templateName) throws IOException {
        return opensearchRepository.isExistsTemplate(templateName);
    }

    @Override
    public boolean hasIndex(String indexName) throws IOException {
        return opensearchRepository.isExistsIndex(indexName);
    }

    @Override
    public List<V2VContent> getDocumentList(DocumentCommand.RegisterRequest documentCommand) {

        SearchRequest searchRequest = new SearchRequest(documentCommand.getTargetIndexList().stream().toArray(String[]::new));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.trackTotalHits(true);
        searchSourceBuilder.query(this._generateQueryByDocumentCommand(documentCommand));
        searchSourceBuilder.timeout(new TimeValue(Criteria.QUERY_TIMEOUT_SECONDS_CRITERIA, TimeUnit.SECONDS));
        searchSourceBuilder.from(documentCommand.getSearchFrom());
        searchSourceBuilder.size(documentCommand.getSearchSize());

        /*order by1이면 colctDt 내림차순, 2이면 score 기준 내림차순*/
        if (documentCommand.getOderBy().equals(QueryOption.ORDERBY_COLCT_DT.getValue())) {
            searchSourceBuilder.sort(QueryKey.COLCT_DT.getValue(), SortOrder.DESC);
        }
        CollapseBuilder collapseBuilder = new CollapseBuilder(QueryKey.REPLACE_TITLE.getValue());
        /*dupliacteYn Option이 있을 경우 */
        if (documentCommand.getDuplicateYn().equals(QueryOption.ENABLE_AGGREGATION.getValue())) {
            InnerHitBuilder innerHitBuilder = new InnerHitBuilder(QueryOption.SAME_REPLACE_TITLE_COUNT.getValue());
            innerHitBuilder.setSize(0);
            collapseBuilder.setInnerHits(innerHitBuilder);
        }

        searchSourceBuilder.collapse(collapseBuilder);
        /*중복제거 후 전체 Count 표기를 위함*/
        searchSourceBuilder
                .aggregation(AggregationBuilders.cardinality(QueryOption.TOTAL_COUNT_AGGREGATION.getValue()).field(QueryKey.REPLACE_TITLE.getValue()));
        searchRequest.source(searchSourceBuilder);
        return opensearchRepository.findAllBySearchCommand(searchRequest)
                .orElseThrow(() -> new DocumentNotFoundException("Requested Documents Not Found"));
    }

    @Override
    public List<V2VContent> getRedundantDocumentList(DocumentCommand.RegisterReplaceTitleRequest request) {

        /*colct_dt 기준 내림차순 정렬*/
        SearchRequest searchRequest = new SearchRequest(request.getTargetIndexList().stream().toArray(String[]::new));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.trackTotalHits(true);
        searchSourceBuilder.query(QueryBuilders.termQuery(QueryKey.REPLACE_TITLE.getValue(), request.getReplaceTitle()));
        searchSourceBuilder.timeout(new TimeValue(Criteria.QUERY_TIMEOUT_SECONDS_CRITERIA, TimeUnit.SECONDS));
        searchSourceBuilder.from(request.getSearchFrom());
        searchSourceBuilder.size(request.getSearchSize());
        searchSourceBuilder.sort(QueryKey.COLCT_DT.getValue(), SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);

        return opensearchRepository.findAllBySearchCommand(searchRequest)
                .orElseThrow(() -> new DocumentNotFoundException("Requested Documents Not Found"));
    }


    private BoolQueryBuilder _generateQueryByDocumentCommand(DocumentCommand.RegisterRequest documentCommand) {

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if (documentCommand.getKeyword() != null) {
            // 한국어, 영어 , 중국어의 경우 docCN , TITLE
            // 위 외 경우 ORG_DOC_CN, ORG_TITLE
            if(_isTargetToOrgField(documentCommand)){
                boolQueryBuilder.must(QueryBuilders.matchQuery(QueryKey.ORG_DOC_CN.getValue(), documentCommand.getKeyword())
                        .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                boolQueryBuilder.should(QueryBuilders.matchQuery(QueryKey.ORG_TITLE.getValue(), documentCommand.getKeyword())
                        .operator(Operator.AND).fuzziness(Fuzziness.AUTO));

            }else{
                boolQueryBuilder.must(QueryBuilders.matchQuery(QueryKey.DOC_CN.getValue(), documentCommand.getKeyword())
                        .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                boolQueryBuilder.should(QueryBuilders.matchQuery(QueryKey.TITLE.getValue(), documentCommand.getKeyword())
                        .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
            }

        } else {
            /*key word 없을 경우 match_all*/
            boolQueryBuilder.must(QueryBuilders.matchAllQuery());
        }

        /*추가 키워드 관련 쿼리 추가*/
        if (documentCommand.getAddKeywords() != null && documentCommand.getAddKeywords().size() > 0) {
            for (SearchDto.AddKeywordInfo keyword : documentCommand.getAddKeywords()) {
                switch (keyword.getOperator().toLowerCase()) {
                    case "and":
                        if(_isTargetToOrgField(documentCommand)){
                            boolQueryBuilder.must(QueryBuilders.matchQuery(QueryKey.ORG_DOC_CN.getValue(), keyword.getKeyword())
                                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                            boolQueryBuilder.should(QueryBuilders.matchQuery(QueryKey.ORG_TITLE.getValue(), keyword.getKeyword())
                                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                        }else{
                            boolQueryBuilder.must(QueryBuilders.matchQuery(QueryKey.DOC_CN.getValue(), keyword.getKeyword())
                                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                            boolQueryBuilder.should(QueryBuilders.matchQuery(QueryKey.TITLE.getValue(), keyword.getKeyword())
                                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                        }
                        break;
                    case "not":
                        if(_isTargetToOrgField(documentCommand)){
                            boolQueryBuilder.mustNot(QueryBuilders.matchQuery(QueryKey.ORG_DOC_CN.getValue(), keyword.getKeyword())
                                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                        }else{
                            boolQueryBuilder.mustNot(QueryBuilders.matchQuery(QueryKey.DOC_CN.getValue(), keyword.getKeyword())
                                    .operator(Operator.AND).fuzziness(Fuzziness.AUTO));
                        }
                        break;
                }
            }
        }
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(QueryKey.COLCT_DATE.getValue());
        rangeQueryBuilder.gte(ConverterUtil.convertLocalDateToLong(documentCommand.getSDate()));
        rangeQueryBuilder.lte(ConverterUtil.convertLocalDateToLong(documentCommand.getEDate()));
        boolQueryBuilder.must(rangeQueryBuilder);

        /*검색 조건별 값 filter 쿼리 생성 */
        List<TermsQueryBuilder> termsQueryBuilderList = new ArrayList<>();
        if (documentCommand.getTypes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.CATEGORY_TYPE.getValue(), documentCommand.getTypes()));
        }
        if (documentCommand.getProductCodes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.PRODUCT_CODE.getValue(), documentCommand.getProductCodes()));
        }
        if (documentCommand.getChannelCodes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.CHANNEL_CODE.getValue(), documentCommand.getChannelCodes()));
        }
        if (documentCommand.getComponentCodes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.COMPONENT_LV2.getValue(), documentCommand.getChannelCodes()));
        }
        if (documentCommand.getOpinionCodes().size() > 0) {
            if (documentCommand.getOrgLangCode().equals(LanguageCode.LANG_CODE_KOREAN.getValue())) {
                termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.PROBLEM_KR.getValue(), documentCommand.getOpinionCodes()));
            } else {
                termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.PROBLEM_EN.getValue(), documentCommand.getOpinionCodes()));
            }
        }
        if (documentCommand.getSiteCodes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.SITE_ID.getValue(), documentCommand.getSiteCodes()));
        }
        if (documentCommand.getItemCodes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.PRODUCT_KEYWORD.getValue(), documentCommand.getItemCodes()));
        }
        if (documentCommand.getGbms().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.GBM.getValue(), documentCommand.getGbms()));
        }
        if (documentCommand.getMakers().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.MAKER.getValue(), documentCommand.getMakers()));
        }
        if (documentCommand.getMainTypes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.MAIN_TYPE.getValue(), documentCommand.getMainTypes()));
        }
        if (documentCommand.getOsVers().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.OS_VER.getValue(), documentCommand.getOsVers()));
        }
        if (documentCommand.getFwVers().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.FW_VER.getValue(), documentCommand.getFwVers()));
        }
        if (documentCommand.getAppCodes().size() > 0) {
            termsQueryBuilderList.add(QueryBuilders.termsQuery(QueryKey.APP_CODES.getValue(), documentCommand.getAppCodes()));
        }

        for (TermsQueryBuilder termsQueryBuilder : termsQueryBuilderList) {
            boolQueryBuilder.filter(termsQueryBuilder);
        }
        return boolQueryBuilder;


    }


    private boolean _isTargetToOrgField(DocumentCommand.RegisterRequest documentCommand){
        if(documentCommand.getOrgLangCode().equals(LanguageCode.LANG_CODE_KOREAN.getValue())
                || documentCommand.getOrgLangCode().equals(LanguageCode.LANG_CODE_CHINESS.getValue())
                || documentCommand.getOrgLangCode().equals(LanguageCode.LANG_CODE_ENGLISH.getValue())){
            return false;
        }
        return true;
    }
}
