package com.purchase.purchase.microservice.dto.resquest;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Objects;

public class PurchaseRequest {

    @NotNull(message = "userId cant be null")
    private Long userId;
    @NotNull(message = "productId cant be null.")
    private Long productId;
    @NotNull(message = "productTypeId cant be null.")
    private Long productTypeId;
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
