package com.samsung.v2v.v2v_search_api.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    DOCUMENT_NOT_FOUND("해당하는 문서가 존재하지 않습니다."),
    INDEX_NOT_FOUND("해당하는 INDEX가 존재하지 않습니다."),
    NETWORK_STATUS_ERROR("Opensearch 서버 네트워크 상태를 확인해주세요."),
    OPENSEARCH_STATUS_ERROR("Opensearch 에러 발생"),

    REQUIRE_BODY_MESSAGE_MISSING("Request Body [JSON] 요청 정보가 입력되지 않았습니다."),

    NOT_SUPPORTED_REQUEST_MEHOTD("지원되지 않는 request method입니다."),
    BAD_REQUEST("요청 Parameter 정보가 잘못되었습니다.");

    private final String errorMsg;
    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
