package com.purchase.purchase.microservice.api.productTypeApi;

import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductTypeClient {

    private final WebClient webClient;

    public ProductTypeClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductTypeResponse getProductTypeById(Long id) {
        return webClient.get()
                .uri("http://localhost:8080/api/product-type/" + id)
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(ResourceNotFoundException::new))
                .bodyToMono(ProductTypeResponse.class)
                .block();
    }
}
