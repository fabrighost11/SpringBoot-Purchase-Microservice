package com.purchase.purchase.microservice.dto.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Purchase request to create or update a purchase")
public class PurchaseRequest {

    @Schema(description = "User ID", example = "1")
    @NotNull(message = "userId cant be null")
    private Long userId;
    @Schema(description = "Product ID", example = "2")
    @NotNull(message = "productId cant be null.")
    private Long productId;
    @Schema(description = "ProductType ID", example = "3")
    @NotNull(message = "productTypeId cant be null.")
    private Long productTypeId;
    @Schema(description = "quantity to be purchased", example = "3")
    @NotNull
    @Min(value = 1, message = "Quantity must be higher than zero.")
    private Integer quantity;

}
