package com.demo.sunat.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Value("${decolecta.token}")
    private String token;

    @Bean
    public RequestInterceptor bearerTokenInterceptor() {
        return requestTemplate ->
                requestTemplate.header("Authorization", "Bearer " + token);
    }
}
