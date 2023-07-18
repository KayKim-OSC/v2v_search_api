package com.samsung.v2v.v2v_search_api.common.exception;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;

public class NetworkException extends BaseException {

    public NetworkException(String message){super(message, ErrorCode.NETWORK_STATUS_ERROR);}
}
