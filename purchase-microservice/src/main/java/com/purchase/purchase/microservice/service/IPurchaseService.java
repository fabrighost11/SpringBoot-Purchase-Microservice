package com.purchase.purchase.microservice.service;

import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IPurchaseService {

    PurchaseResponse getPurchaseById(Long id) throws ResourceNotFoundException;
    List<PurchaseResponse> getAllPurchases();
    Mono<PurchaseResponse> createPurchase(PurchaseRequest purchaseRequest) throws Exception;
    void deletePurchase(Long id) throws Exception;
}
