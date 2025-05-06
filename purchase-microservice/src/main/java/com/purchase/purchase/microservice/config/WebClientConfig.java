package com.purchase.purchase.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient productClient(){
        return WebClient.builder().baseUrl("http://localhost:8080/api/product").build();
    }

    @Bean
    public WebClient userClient(){
        return WebClient.builder().baseUrl("http://localhost:8081/api/user").build();
    }

    @Bean
    public WebClient productTypeClient(){
        return WebClient.builder().baseUrl("http://localhost:8080/api/product-type").build();
    }
}
