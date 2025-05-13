package com.purchase.purchase.microservice.api.userApi;

import com.purchase.purchase.microservice.dto.response.UserResponse;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UserResponse getUserById(Long id) {
        return  webClient.get()
                .uri("http://localhost:8081/api/user/" + id)
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> response.bodyToMono(String.class).map(ResourceNotFoundException::new))
                .bodyToMono(UserResponse.class)
                .block();
    }
}
