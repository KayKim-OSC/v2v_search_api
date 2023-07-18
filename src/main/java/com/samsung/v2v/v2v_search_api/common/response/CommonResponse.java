package com.samsung.v2v.v2v_search_api.common.response;

import com.samsung.v2v.v2v_search_api.common.constants.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
#############################################################
* ClassName   : CommonResponse
* Description : Response 메시지 포멧
 *              status : HTTP Status Code
 *              result : SUCCESS / FAIL
 *              data : Reply Data
 *              message : 메시지
 *              errorCode : 에러코드 ---> ErrorCode 하위 내용
* date        : 2022-10-25
* Version     :
#############################################################
*/

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private int status;
    private Result result;
    private Long totalCount;
    private T data;
    private String message;
    private String errorCode;

    public static <T> CommonResponse<T> success(T data, String message,Long totalCount) {
        return (CommonResponse<T>) CommonResponse.builder()
                .status(HttpStatus.OK.value())
                .result(Result.SUCCESS)
                .totalCount(totalCount)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> success(T data,Long totalCount) {
        return success(data, null,totalCount);
    }

    public static CommonResponse fail(int code,String message, String errorCode) {
        return CommonResponse.builder()
                .status(code)
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    public static CommonResponse fail(int code, ErrorCode errorCode) {
        return CommonResponse.builder()
                .status(code)
                .result(Result.FAIL)
                .message(errorCode.getErrorMsg())
                .errorCode(errorCode.name())
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }

}
