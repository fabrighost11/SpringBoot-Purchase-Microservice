package com.purchase.purchase.microservice.repository;

import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    Optional<Purchase> findById(Long id);
    List<Purchase> findPurchaseByProductTypeId(Long id);
    List<Purchase> findPurchaseByUserId(Long userId);
}
