package com.samsung.v2v.v2v_search_api.common.util;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;
import com.samsung.v2v.v2v_search_api.common.constants.Prefix;
import com.samsung.v2v.v2v_search_api.common.constants.QueryOption;
import com.samsung.v2v.v2v_search_api.common.exception.InvalidParamException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class IndexGenerator {

    public static String createSynonymIndexName(LocalDate currentDate, String langCode){
        return new StringBuilder(Prefix.INDEX_NAME_PREFIX.getValue())
                .append(currentDate.getYear())
                .append(String.format("%02d", currentDate.getMonthValue()))
                .append("_")
                .append(langCode)
                .append("_")
                .append(Prefix.SYNONYM.getValue())
                .toString();
    }

    public static String createDefaultIndexName(LocalDate currentDate, String langCode) {
        return new StringBuilder(Prefix.INDEX_NAME_PREFIX.getValue())
                .append(currentDate.getYear())
                .append(String.format("%02d", currentDate.getMonthValue()))
                .append("_")
                .append(langCode)
                .toString();
    }

    public static List<String> createTargetIndexs(String synonymYn, LocalDate sDate, LocalDate eDate, String langCode) {
        List<String> result = new ArrayList<>();
        if (sDate.isAfter(eDate)) {
            throw new InvalidParamException(ErrorCode.BAD_REQUEST.getErrorMsg() + " sDate : " + sDate + " eDate : " + eDate);
        }
        if (LocalDate.now().isBefore(eDate)) {
            eDate = LocalDate.now();
        }
        sDate = sDate.withDayOfMonth(1);
        eDate = eDate.withDayOfMonth(1);
        /*synonym 조건절에 따른 TargetIndex 구분*/
        if(synonymYn.equals(QueryOption.DISABLE_SYNOYM.getValue())){
            Stream.iterate(sDate, date -> date.plusMonths(1))
                    .limit(ChronoUnit.MONTHS.between(sDate, eDate) + 1)
                    .forEach(current -> result.add(IndexGenerator.createDefaultIndexName(current, langCode)));
        }else{
            Stream.iterate(sDate, date -> date.plusMonths(1))
                    .limit(ChronoUnit.MONTHS.between(sDate, eDate) + 1)
                    .forEach(current -> result.add(IndexGenerator.createSynonymIndexName(current, langCode)));
        }
        return result;
    }

}
