package com.samsung.v2v.v2v_search_api.common.response;

import com.google.common.collect.Lists;
import com.samsung.v2v.v2v_search_api.common.constants.CommonKey;
import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;
import com.samsung.v2v.v2v_search_api.common.exception.BaseException;
import com.samsung.v2v.v2v_search_api.common.exception.DocumentNotFoundException;
import com.samsung.v2v.v2v_search_api.common.exception.IndexNotFoundException;
import com.samsung.v2v.v2v_search_api.common.exception.InvalidParamException;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.OpenSearchException;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * #############################################################
 * ClassName   : CommonCotrollerAdvice
 * Description : Exception Event Handler 역할 담당
 * 추후 Exception 추가 시 해당 부분에 이벤트 핸들러 구현 필요
 * date        : 2022-10-24
 * Version     : 1.0.0
 * #############################################################
 */

@ControllerAdvice
@Slf4j
public class CommonCotrollerAdvice {

    private static final List<ErrorCode> SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST = Lists.newArrayList();

    /**
     * #############################################################
     * MethodName   : onException
     * Description : Exception 클래스 이벤트 핸들러
     * Parameter   : Exception.class
     * Return      : CommonResponse
     * #############################################################
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        log.error("reqeustId = {} ", eventId, e);
        return CommonResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.COMMON_SYSTEM_ERROR);
    }

    /**
     * #############################################################
     * MethodName   : onBaseException
     * Description :  서버의 BaseException  이벤트 핸들러
     * Parameter   :  BaseException
     * Return      : CommonResponse
     * #############################################################
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public CommonResponse onBaseException(BaseException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[BaseException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[BaseException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e.getErrorCode().name());
    }


    /**
     * #############################################################
     * MethodName   : onBaseException
     * Description :  DocumentNotFoundException  이벤트 핸들러
     * Parameter   :  DocumentNotFoundException
     * Return      : CommonResponse
     * #############################################################
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = DocumentNotFoundException.class)
    public CommonResponse onDocumentNotFoundException(DocumentNotFoundException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[DocumentNotFoundException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[DocumentNotFoundException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getErrorCode().name());
    }

    /**
     * #############################################################
     * MethodName   : onHttpMessageNotReadableException
     * Description : HttpMessageNotReadableException 이벤트 핸들러
     * Parameter   :  HttpMessageNotReadableException
     * Return      :
     * #############################################################
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public CommonResponse onHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        log.error("[HttpMessageNotReadableException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage(),
        e.getMessage());
        return CommonResponse.fail(HttpStatus.BAD_REQUEST.value(),ErrorCode.REQUIRE_BODY_MESSAGE_MISSING.getErrorMsg(),ErrorCode.REQUIRE_BODY_MESSAGE_MISSING.name());
    }



    /**
    #############################################################
    * MethodName   : onHttpRequestMethodNotSupportedException
    * Description : onHttpRequestMethodNotSupportedException 이벤트 핸들러
    * Parameter   :  HttpRequestMethodNotSupportedException
    * Return      :
    #############################################################
    */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public CommonResponse onHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        log.error("[HttpMessageNotReadableException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage(),
                e.getMessage());
        return CommonResponse.fail(HttpStatus.BAD_REQUEST.value(),e.getMessage(),ErrorCode.NOT_SUPPORTED_REQUEST_MEHOTD.name());
    }



    /**
     * #############################################################
     * MethodName   : onIndexNotFoundException
     * Description : IndexNotFoundException 이벤트 핸들러
     * Parameter   :  IndexNotFoundException
     * Return      :
     * #############################################################
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IndexNotFoundException.class})
    public CommonResponse onIndexNotFoundException(IndexNotFoundException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.warn("[IndexNotFoundException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[IndexNotFoundException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getErrorCode().name());
    }

    /**
     * #############################################################
     * MethodName   : onInvalidParamException
     * Description : InvalidParamException 이벤트 핸들러
     * Parameter   : InvalidParamExcetpion
     * Return      : CommonResponse
     * #############################################################
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvalidParamException.class)
    public CommonResponse onInvalidParamException(InvalidParamException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        if (SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[InvalidParamException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[InvalidParamException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                    NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getErrorCode().name());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = OpenSearchException.class)
    public CommonResponse onOpenSearchException(OpenSearchException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        log.warn("[InvalidParamException] reqeustId = {}, cause = {}, errorMsg = {}", eventId,
                NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(e.status().getStatus(), e.getMessage(), ErrorCode.OPENSEARCH_STATUS_ERROR.name());
    }

    /**
     * #############################################################
     * MethodName   : methodArgumentNotValidException
     * Description :  Request Data Format이 잘못된 경우
     * Parameter   :  MethodArgumentNotValidException
     * Return      :  CommonResponse
     * #############################################################
     */

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String eventId = MDC.get(CommonKey.HEADER_REQUEST_UUID_KEY.getValue());
        log.warn("[MethodArgumentNotValidException] reqeustId = {}, errorMsg = {}", eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        if (fe != null) {
            String message = "Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")";
            return CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), message, ErrorCode.BAD_REQUEST.name());
        } else {
            return CommonResponse.fail(HttpStatus.BAD_REQUEST.value(), ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg(), ErrorCode.BAD_REQUEST.name());
        }
    }
}
