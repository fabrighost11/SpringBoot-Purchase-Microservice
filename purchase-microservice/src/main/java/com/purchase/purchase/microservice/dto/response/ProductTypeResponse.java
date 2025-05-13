package com.purchase.purchase.microservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Response to ProductType requests made")
public class ProductTypeResponse {

    @Schema(description = "ProductType ID", example = "1")
    private Long id;
    @Schema(description = "ProductType name", example = "SPORTS")
    private String name;

    public ProductTypeResponse() {
    }

    public ProductTypeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductTypeResponse that = (ProductTypeResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
