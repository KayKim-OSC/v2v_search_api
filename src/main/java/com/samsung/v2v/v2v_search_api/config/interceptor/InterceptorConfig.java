package com.samsung.v2v.v2v_search_api.config.interceptor;

import com.samsung.v2v.v2v_search_api.common.interceptor.CommonRequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final CommonRequestInterceptor commonRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonRequestInterceptor)
                .addPathPatterns("/api/v1/**");
    }
}
