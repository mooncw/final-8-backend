package com.fastcampus.befinal.common.config;

import com.fastcampus.befinal.common.filter.RequestBodyCachingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class HttpRequestConfig {

    @Bean
    public FilterRegistrationBean reRequestFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new RequestBodyCachingFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
}
