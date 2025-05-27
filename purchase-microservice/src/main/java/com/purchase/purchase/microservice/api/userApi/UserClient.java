package com.purchase.purchase.microservice.api.userApi;

import com.purchase.purchase.microservice.dto.response.UserResponse;
import com.purchase.purchase.microservice.exception.ForbiddenAccessException;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserResponse getUserById(Long id, String token) {
        return  webClient.get()
                .uri("http://localhost:8081/api/user/" + id)
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
                        throw new ResourceNotFoundException("User not found");
                    }
                    return Mono.error(new RuntimeException("Client error"));
                })
                .bodyToMono(UserResponse.class)
                .block();
    }
}
