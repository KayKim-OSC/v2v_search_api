package com.samsung.v2v.v2v_search_api.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueryOption {
    TERM_AGGREGATION_NAME("term-agg"),
    TOTAL_COUNT_AGGREGATION("totalCount"),

    SAME_REPLACE_TITLE_COUNT("replaceTitleCount"),
    ORDER_BY_SCORE("2"),
    ORDERBY_COLCT_DT("1"),
    ENABLE_SYNONYM("Y"),
    DISABLE_SYNOYM("N"),
    ENABLE_AGGREGATION("Y"),
    DISABLE_AGGREGATION("N");
    private final String value;
}
