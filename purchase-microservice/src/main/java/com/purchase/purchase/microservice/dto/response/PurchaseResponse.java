package com.purchase.purchase.microservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response to purchase requests made")
public class PurchaseResponse {

    @Schema(description = "Purchase ID", example = "1")
    private Long id;
    @Schema(description = "User ID", example = "2")
    private Long userId;
    @Schema(description = "Product ID", example = "1")
    private Long productId;
    @Schema(description = "ProductType ID", example = "1")
    private Long productTypeId;
    @Schema(description = "Product quantity purchased", example = "5")
    private Integer quantity;
}
