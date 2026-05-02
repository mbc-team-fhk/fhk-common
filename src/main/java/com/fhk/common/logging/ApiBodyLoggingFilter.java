package com.fhk.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Profile("!staging & !prod")
public class ApiBodyLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long elapsed = System.currentTimeMillis() - start;

            String requestBody = StreamUtils.copyToString(
                    wrappedRequest.getInputStream(),
                    StandardCharsets.UTF_8
            );

            String responseBody = new String(
                    wrappedResponse.getContentAsByteArray(),
                    StandardCharsets.UTF_8
            );

            String query = request.getQueryString();
            String uri = query == null
                    ? request.getRequestURI()
                    : request.getRequestURI() + "?" + query;

            log.info("""
                    
                    [API LOG]
                    Request  : {} {}
                    Req Body : {}
                    Response : status={} elapsed={}ms
                    Res Body : {}
                    """,
                    request.getMethod(),
                    uri,
                    emptyToDash(requestBody),
                    wrappedResponse.getStatus(),
                    elapsed,
                    emptyToDash(responseBody)
            );

            wrappedResponse.copyBodyToResponse();
        }
    }

    private String emptyToDash(String value) {
        if (value == null || value.isBlank()) {
            return "-";
        }
        return value;
    }
}