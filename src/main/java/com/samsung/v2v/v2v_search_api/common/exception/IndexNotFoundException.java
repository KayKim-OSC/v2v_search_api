package com.samsung.v2v.v2v_search_api.common.exception;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;

public class IndexNotFoundException extends BaseException {

    public IndexNotFoundException(String message){super(message, ErrorCode.INDEX_NOT_FOUND);}
}
