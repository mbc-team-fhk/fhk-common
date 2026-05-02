package com.fhk.common.logging.config;

import com.fhk.common.logging.ApiBodyLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;

@Configuration
@Profile("!staging & !prod")
public class ApiLoggingFilterConfig {

    @Bean
    public FilterRegistrationBean<ApiBodyLoggingFilter> apiBodyLoggingFilterRegistration() {
        FilterRegistrationBean<ApiBodyLoggingFilter> registration = new FilterRegistrationBean<>();

        registration.setFilter(new ApiBodyLoggingFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);

        return registration;
    }
}