package com.purchase.purchase.microservice.api.productTypeApi;

import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.exception.ForbiddenAccessException;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.exception.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductTypeClient {

    private final WebClient webClient;

    public ProductTypeClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ProductTypeResponse getProductTypeById(Long id, String token) {
        return webClient.get()
                .uri("http://localhost:8080/api/product-type/" + id)
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
                        throw new ResourceNotFoundException("Product-type not found");
                    }
                    return Mono.error(new RuntimeException("Client error"));
                })
                .bodyToMono(ProductTypeResponse.class)
                .block();
    }
}
