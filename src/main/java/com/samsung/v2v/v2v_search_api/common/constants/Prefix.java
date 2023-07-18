package com.samsung.v2v.v2v_search_api.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Prefix{

    LOG_REQUEST_PREFIX("[Request]"),
    LOG_RESPONSE_PREFIX("[Response]"),
    LOG_END_PREFIX("[End]"),
    INDEX_NAME_PREFIX("ss_voc_contents_"),
    SYNONYM("synonym"),
    INDEX_TEMPLATE_NAME_PREFIX("ss_voc_contents_template_");

    private final String value;
}