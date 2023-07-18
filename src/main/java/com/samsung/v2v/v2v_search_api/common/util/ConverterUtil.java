package com.samsung.v2v.v2v_search_api.common.util;

import com.samsung.v2v.v2v_search_api.common.exception.InvalidParamException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConverterUtil {

    public static Long convertLocalDateToLong(LocalDate date) {
        String stringDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return Long.parseLong(stringDate);
    }

    public static LocalDate convertStringtoLocalDate(String time) {
        try{
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(time, dateTimeFormatter);
        }catch (RuntimeException e){
            throw new InvalidParamException("please check request data format = [('YYYYMMDD')] , requested format= [" + time+"]");
        }
    }

}
