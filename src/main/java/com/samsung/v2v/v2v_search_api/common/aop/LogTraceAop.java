package com.samsung.v2v.v2v_search_api.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.v2v.v2v_search_api.common.constants.CommonKey;
import com.samsung.v2v.v2v_search_api.common.constants.Prefix;
import com.samsung.v2v.v2v_search_api.common.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
#############################################################
* ClassName   : LogTraceAop
* Description : Controller 별 로깅 AOP 클래스
 *                로깅 목록 :
 *                  1. 처리시간
 *                  2 요청 정보내역
 *                AOP Target Area : Controller
* date        : 2022-10-24
* Version     : 1.0.0
#############################################################
*/


@Component
@Aspect
@Slf4j
public class LogTraceAop {

    @Pointcut("within(com.samsung.v2v.v2v_search_api.interfaces..*)")
    public void controllerRequest(){}


    /*Controller 서비스 처리과정 Logging Aop*/
    @Around("controllerRequest()")
    public Object controllerLogTrace(ProceedingJoinPoint joinPoint) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object result = null;
            log.info("{} method={}, uri = {}",
                    Prefix.LOG_REQUEST_PREFIX.getValue(),
                    MDC.get(CommonKey.REQUEST_METHOD_KEY.getValue()),
                    MDC.get(CommonKey.REQUEST_URI_KEY.getValue()));


            try{
               result = joinPoint.proceed();
               return result;
            }finally {
                long endTime = System.currentTimeMillis();
                if(result!=null){
                    CommonResponse commonResponse = (CommonResponse) result;
                    log.info("{} status code : {} [{}]",
                            Prefix.LOG_RESPONSE_PREFIX.getValue()
                            ,commonResponse.getStatus()
                            ,commonResponse.getResult());
                    log.debug("{} {}",
                            Prefix.LOG_RESPONSE_PREFIX.getValue()
                            , new ObjectMapper().writeValueAsString(result));
                }
                log.info("{} elapsedTime: {} ms",
                        Prefix.LOG_END_PREFIX.getValue()
                        ,(endTime-startTime));

            }
    }

}
