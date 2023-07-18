package com.samsung.v2v.v2v_search_api.common.exception;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;

public class InvalidParamException extends BaseException {

    public InvalidParamException(String errorMsg) {
        super(errorMsg, ErrorCode.COMMON_INVALID_PARAMETER);
    }

}
