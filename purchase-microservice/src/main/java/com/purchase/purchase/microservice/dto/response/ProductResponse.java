package com.purchase.purchase.microservice.dto.response;

import java.util.Objects;

public class ProductResponse {

    private Long id;
    private String name;
    private Double price;
    private Integer stock;
    private String productType;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, Double price, Integer stock, String productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.productType = productType;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getProductTypeId() {
        return productType;
    }

    public void setProductTypeId(String productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponse that = (ProductResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(stock, that.stock) && Objects.equals(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock, productType);
    }
}
