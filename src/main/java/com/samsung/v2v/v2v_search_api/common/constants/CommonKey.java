package com.samsung.v2v.v2v_search_api.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommonKey{
    HEADER_REQUEST_UUID_KEY("trace-id"),
    REQUEST_URI_KEY("reqeust-uri"),
    REQUEST_METHOD_KEY("reqeust-method"),
    COUNT_KEY("count"),
    DATA_KEY("data");
    private final String value;
}
