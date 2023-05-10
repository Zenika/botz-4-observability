package com.zenika.botz.observability.common.logging;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@AutoConfiguration
public class ServiceLoggerAutoConfiguration {

    @Bean
    public OncePerRequestFilter addRequestInContext(){
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws ServletException, IOException {
                MDC.put("request.method", request.getMethod());
                MDC.put("request.uri", request.getRequestURI());
                chain.doFilter(request, response);
                MDC.remove("request.method");
                MDC.remove("request.uri");
            }
        };
    }

}
