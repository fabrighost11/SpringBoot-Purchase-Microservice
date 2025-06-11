package com.purchase.purchase.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("Purchase Microservice API")
                    .description("Documentation about purchase microservice endpoints")
                    .version("1.0.0"))
            .addServersItem(new Server()
                    .url("http://localhost:8082/")
                    .description("Servidor local"));
    }
}
