package com.purchase.purchase.microservice.dto.response;

import java.util.Objects;

public class PurchaseResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;

    public PurchaseResponse() {
    }

    public PurchaseResponse(Long id, Long userId, Long productId, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
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
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, quantity);
    }
}
