package com.purchase.purchase.microservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response to users requests made")
public class UserResponse {

    @Schema(description = "ID of the user", example = "1")
    private Long id;
    @Schema(description = "Name of the user", example = "Maria")
    private String name;
    @Schema(description = "Email of the user", example = "maria@gmail.com")
    private String email;
    @Schema(description = "Role of the user. Can be 'ADMIN' or 'DEFAULT_USER'", example = "ADMIN")
    private String role;

}
