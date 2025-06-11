package com.purchase.purchase.microservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response to ProductType requests made")
public class ProductTypeResponse {

    @Schema(description = "ProductType ID", example = "1")
    private Long id;
    @Schema(description = "ProductType name", example = "SPORTS")
    private String name;

}
