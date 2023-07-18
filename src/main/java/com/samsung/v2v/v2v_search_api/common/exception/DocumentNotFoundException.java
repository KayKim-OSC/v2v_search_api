package com.samsung.v2v.v2v_search_api.common.exception;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;

public class DocumentNotFoundException extends BaseException {
    public DocumentNotFoundException(String message){super(message, ErrorCode.DOCUMENT_NOT_FOUND);}
}
