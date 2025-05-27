package com.purchase.purchase.microservice.api.productApi;

import com.purchase.purchase.microservice.dto.response.ProductResponse;
import com.purchase.purchase.microservice.exception.ForbiddenAccessException;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public ProductResponse getProductById(Long id, String token) throws ResourceNotFoundException {
        return webClient.get()
                .uri("http://localhost:8080/api/product/" + id)
                .header(HttpHeaders.AUTHORIZATION,token)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.UNAUTHORIZED) {
                        throw new UnauthorizedException("Unauthorized access - Invalid token");
                    }
                    if (clientResponse.statusCode() == HttpStatus.FORBIDDEN) {
                        throw new ForbiddenAccessException("Forbidden - Insufficient permissions");
                    }
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        throw new ResourceNotFoundException("Product not found");
                    }
                    return Mono.error(new RuntimeException("Client error"));
                })
                .bodyToMono(ProductResponse.class)
                .block();
    }

    public void decreaseProductQuantity(Long id ,Integer quantity,String token) throws ResourceNotFoundException {
        webClient.put()
                .uri("http://localhost:8080/api/product/" + id + "/stock/decrease" )
                .header(HttpHeaders.AUTHORIZATION,token)
                .bodyValue(Map.of("quantity", quantity))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatus.UNAUTHORIZED) {
                        throw new UnauthorizedException("Unauthorized access - Invalid token");
                    }
                    if (clientResponse.statusCode() == HttpStatus.FORBIDDEN) {
                        throw new ForbiddenAccessException("Forbidden - Insufficient permissions");
                    }
                    if (clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        throw new ResourceNotFoundException("Product not found");
                    }
                    return Mono.error(new RuntimeException("Client error"));
                })
                .toBodilessEntity()
                .block();
    }
}
