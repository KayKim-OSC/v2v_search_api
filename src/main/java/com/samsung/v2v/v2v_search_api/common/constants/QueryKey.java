package com.samsung.v2v.v2v_search_api.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum QueryKey {

    DOC_ID("doc_id"),
    DOC_CN("doc_cn"),
    ORG_DOC_CN("org_doc_cn"),
    ORG_TITLE("org_title"),
    TITLE("title"),
    COLCT_DATE("colct_date"),
    COLCT_DT("colct_dt"),
    CATEGORY_TYPE("category_type"),
    PRODUCT_CODE("product_code"),
    CHANNEL_CODE("channel_code"),
    COMPONENT_LV2("component_lv2"),
    PROBLEM_KR("problem_kr"),
    PROBLEM_EN("problem_en"),
    SITE_ID("site_id"),

    PRODUCT_KEYWORD("product_keyword"),

    GBM("gbm"),

    MAKER("company"),
    MAIN_TYPE("main_type"),
    OS_VER("os_ver"),
    FW_VER("fw_ver"),
    APP_CODES("app_id"),

    REPLACE_TITLE("replace_title");
    private final String value;
}
