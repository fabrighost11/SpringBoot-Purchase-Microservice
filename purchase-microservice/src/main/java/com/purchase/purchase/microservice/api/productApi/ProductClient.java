package com.purchase.purchase.microservice.api.productApi;

import com.purchase.purchase.microservice.dto.response.ProductResponse;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public ProductResponse getProductById(Long id) throws ResourceNotFoundException {
        return webClient.get()
                .uri("http://localhost:8080/api/product/" + id)
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(ResourceNotFoundException::new))
                .bodyToMono(ProductResponse.class)
                .block();
    }

    public void decreaseProductQuantity(Long id ,Integer quantity) throws ResourceNotFoundException {
        webClient.put()
                .uri("http://localhost:8080/api/product/" + id + "/stock/decrease" )
                .bodyValue(Map.of("quantity", quantity))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> { throw new RuntimeException("Failed to update stock" + response.statusCode()); })
                .toBodilessEntity()
                .block();
    }
}
