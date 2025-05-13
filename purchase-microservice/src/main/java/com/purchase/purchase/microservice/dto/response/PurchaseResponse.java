package com.purchase.purchase.microservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

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

    public PurchaseResponse() {
    }

    public PurchaseResponse(Long id, Long userId, Long productId, Long productTypeId, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        PurchaseResponse that = (PurchaseResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId) && Objects.equals(productTypeId, that.productTypeId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, productTypeId, quantity);
    }
}
