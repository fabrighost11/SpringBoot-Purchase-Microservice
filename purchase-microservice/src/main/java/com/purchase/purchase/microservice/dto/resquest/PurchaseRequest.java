package com.purchase.purchase.microservice.dto.resquest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;
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

    public PurchaseRequest() {
    }

    public PurchaseRequest(Long userId, Long productId, Long productTypeId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRequest that = (PurchaseRequest) o;
        return Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId) && Objects.equals(productTypeId, that.productTypeId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId, productTypeId, quantity);
    }
}
