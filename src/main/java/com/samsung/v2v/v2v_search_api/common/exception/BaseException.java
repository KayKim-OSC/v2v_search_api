package com.samsung.v2v.v2v_search_api.common.exception;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private ErrorCode errorCode;

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
