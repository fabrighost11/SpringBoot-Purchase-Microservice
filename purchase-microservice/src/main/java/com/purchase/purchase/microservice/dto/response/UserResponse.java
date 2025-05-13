package com.purchase.purchase.microservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

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

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, role);
    }
}
