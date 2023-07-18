package com.samsung.v2v.v2v_search_api.common.interceptor;

import com.samsung.v2v.v2v_search_api.common.constants.CommonKey;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
#############################################################
* ClassName   : CommonRequestInterceptor
* Description :    요청 Thread 별 요청정보(request uri, method 정보)를
 *                  Slf4j MDC(Thread Local 저장소)에 저장
* date        : 2022-10-24
* Version     : 1.0.0
#############################################################
*/


@Component
public class CommonRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(CommonKey.HEADER_REQUEST_UUID_KEY.getValue(), UUID.randomUUID().toString());
        MDC.put(CommonKey.REQUEST_URI_KEY.getValue(), request.getRequestURI());
        MDC.put(CommonKey.REQUEST_METHOD_KEY.getValue(), request.getMethod());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.clear();
    }
}
