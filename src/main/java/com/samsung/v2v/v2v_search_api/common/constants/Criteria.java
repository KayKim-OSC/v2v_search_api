package com.samsung.v2v.v2v_search_api.common.constants;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
#############################################################
* ClassName   : Criteria
* Description : 상수 값 관리 클래스
* date        : 2022-10-25
* Version     :
#############################################################
*/


@Component
@Primary
public class Criteria {

    public static int INDEX_LIFECYCLE_MONTH_CRITERIA;
    public static int QUERY_TIMEOUT_SECONDS_CRITERIA;
    public static int DEFAULT_PAGING_SIZE_COUNT;
    public static int SCHEDULING_THREAD_POOL_SIZE;


    @Value("${opensearch.setting-info.index.life-cycle}")
    public void setIndexLifecycleMonthCriteriaKey(int key){
        INDEX_LIFECYCLE_MONTH_CRITERIA = key;
    }
    @Value("${opensearch.setting-info.query.time-out}")
    public void setQueryTimeoutSecondsCriteria(int timeout){
        QUERY_TIMEOUT_SECONDS_CRITERIA = timeout;
    }
    @Value("${application.default.paging-size}")
    public void setDefaultPagingSizeCount(int count){DEFAULT_PAGING_SIZE_COUNT = count;}
    @Value("${application.scheduling.pool-size}")
    public void setSchedulingThreadPoolSize(int size){SCHEDULING_THREAD_POOL_SIZE = size;}
}
