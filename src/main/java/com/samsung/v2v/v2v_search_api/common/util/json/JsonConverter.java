package com.samsung.v2v.v2v_search_api.common.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.v2v.v2v_search_api.domain.v2v.V2VContent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
#############################################################
* ClassName   : JsonConverter
* Description : Json <-> Object Converter 기능 구현
* date        : 2022-10-24
* Version     : 1.0.0
#############################################################
*/

@Component
public class JsonConverter {

    public V2VContent toV2vContent(Map<String,Object> before) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(before, V2VContent.class);
    }
}
