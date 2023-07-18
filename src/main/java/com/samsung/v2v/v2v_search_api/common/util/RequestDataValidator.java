package com.samsung.v2v.v2v_search_api.common.util;

import com.samsung.v2v.v2v_search_api.common.constants.Criteria;
import com.samsung.v2v.v2v_search_api.common.exception.IndexNotFoundException;
import com.samsung.v2v.v2v_search_api.interfaces.v2v.SearchDto;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestDataValidator {

    public static List<SearchDto.AddKeywordInfo> validateInitializeAddKeyword(List<SearchDto.AddKeywordInfo> request){
        if(!request.isEmpty()){
            if(request.get(0).getKeyword()==null){
                return null;
            }
            return request;
        }
        return null;
    }

    public static List<String> validateInitializeData(List<String> from) {
        List<String> result = new ArrayList<>();
        if (from.size() == 1) {
            if (StringUtils.isEmpty(from.get(0))) {
                return result;
            }
        }
        return from;
    }




    public static LocalDate validateExistsIndexDate(LocalDate criteria) {
        LocalDate criteriaDate = LocalDate.now().withDayOfMonth(1).minusMonths(Criteria.INDEX_LIFECYCLE_MONTH_CRITERIA);
        if (criteria.withDayOfMonth(1).isBefore(criteriaDate)) {
            throw new IndexNotFoundException("Index not founded date ={"+criteria+"} \n The data storage period is three years. ");
        }
        return criteria;
    }
}
