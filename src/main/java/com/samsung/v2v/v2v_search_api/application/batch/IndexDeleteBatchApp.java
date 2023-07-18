package com.samsung.v2v.v2v_search_api.application.batch;

import com.samsung.v2v.v2v_search_api.common.constants.Criteria;
import com.samsung.v2v.v2v_search_api.common.constants.LanguageCode;
import com.samsung.v2v.v2v_search_api.common.util.IndexGenerator;
import com.samsung.v2v.v2v_search_api.domain.v2v.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
#############################################################
* ClassName   : IndexBatchDeleteApp
* Description : 인덱스 삭제 담당 (인덱스 보관 기간 : 3 year)
* date        : 2022-10-24
* Version     : 1.0.0
#############################################################
*/

@Component
@Slf4j
@RequiredArgsConstructor
public class IndexDeleteBatchApp {

    private final DocumentService documentService;


    /**
    #############################################################
    * MethodName   : deleteOldIndex
    * Description :  매월 1일 01:00 기준 3년전 인덱스 삭제
    * Parameter   :  @Scheduled(cron =    "0 0 1 1 * *")
    * Return      : void
    #############################################################
    */

    @Scheduled(cron = "0 0 1 1 * *")
    public void deleteOldIndex(){
        log.info("[start] Delete Batch Process stated");
        Map<String, List<String>> deleteSets = this._getDeleteTargetDateIndexName(Criteria.INDEX_LIFECYCLE_MONTH_CRITERIA);
        documentService.deleteOldIndexDocuemnt(deleteSets);
        log.info("[end] Delete Batch Process ended");
    }
    /**
    #############################################################
    * MethodName   : _getDeleteTargetDateIndexName
    * Description : 삭제 대상 인덱스 목록 생성
    * Parameter   : 인덱스 보관기간
     *              삭제 대상 : (현재시간 - 인덱스 보관기간[3년]) 이전 데이터
    * Return      :  Map(Language_Code,삭제 대상 인덱스명)
    #############################################################
    */

    private Map<String, List<String>> _getDeleteTargetDateIndexName(int deleteCriteria){
        Map<String, List<String>> target = new HashMap<>();
        LocalDate targetDay = LocalDate.now().withDayOfMonth(1).minusMonths(deleteCriteria);
        for(LanguageCode languageCode : LanguageCode.values()){
            List<String> indexNames = new ArrayList<>();
            indexNames.add(IndexGenerator.createSynonymIndexName(targetDay, languageCode.getValue()));
            indexNames.add(IndexGenerator.createDefaultIndexName(targetDay, languageCode.getValue()));
            target.put(languageCode.name(), indexNames);
        }
        return target;
    }
}
