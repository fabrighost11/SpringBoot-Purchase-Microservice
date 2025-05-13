package com.purchase.purchase.microservice.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "PURCHASE")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_type_id")
    private Long productTypeId;
    @Column(name = "quantity")
    private Integer quantity;

    public Purchase() {
    }

    public Purchase(Long id, Long userId, Long productId, Long productTypeId, Integer quantity) {
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
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id) && Objects.equals(userId, purchase.userId) && Objects.equals(productId, purchase.productId) && Objects.equals(productTypeId, purchase.productTypeId) && Objects.equals(quantity, purchase.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, productTypeId, quantity);
    }
}
