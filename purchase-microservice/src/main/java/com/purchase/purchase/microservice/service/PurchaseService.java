package com.purchase.purchase.microservice.service;

import com.purchase.purchase.microservice.dto.response.ProductResponse;
import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.response.UserResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.model.Purchase;
import com.purchase.purchase.microservice.repository.PurchaseRepository;
import com.purchase.purchase.microservice.api.productApi.ProductClient;
import com.purchase.purchase.microservice.api.productTypeApi.ProductTypeClient;
import com.purchase.purchase.microservice.api.userApi.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {


    private final PurchaseRepository purchaseRepository;

    private final UserClient userClient;
    private final ProductClient productClient;
    private final ProductTypeClient productTypeClient;


    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository, UserClient userClient, ProductClient productClient, ProductTypeClient productTypeClient) {
        this.purchaseRepository = purchaseRepository;
        this.userClient = userClient;
        this.productClient = productClient;
        this.productTypeClient = productTypeClient;
    }

    public PurchaseResponse getPurchaseById(Long id) throws ResourceNotFoundException {
         Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));
         return convertToPurchaseResponse(purchase);
    }

    public List<PurchaseResponse> getAllPurchases(){
        return purchaseRepository.findAll()
                .stream()
                .map(this::convertToPurchaseResponse)
                .collect(Collectors.toList());
    }

    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest, String token){

        ProductResponse product = productClient.getProductById(purchaseRequest.getProductId(), token);
        ProductTypeResponse productType = productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId(), token);


        if(!product.getProductTypeId().equals(productType.getId())) throw new IllegalArgumentException("Product type does not match with the current product");
        if (product.getStock() < purchaseRequest.getQuantity()) throw new IllegalArgumentException("Insufficient stock");

        Purchase purchase = new Purchase();
        purchase.setProductId(purchaseRequest.getProductId());
        purchase.setUserId(purchaseRequest.getUserId());
        purchase.setProductTypeId(productType.getId());
        purchase.setQuantity(purchaseRequest.getQuantity());

        productClient.decreaseProductQuantity(purchase.getProductId(), purchase.getQuantity(), token);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        return convertToPurchaseResponse(savedPurchase);

    }

    public List<PurchaseResponse> getAllPurchasesByUserId(Long userId, String token){

        if (userClient.getUserById(userId, token) == null) throw new ResourceNotFoundException("User not found");

        return purchaseRepository.findPurchaseByUserId(userId)
                .stream()
                .map(this::convertToPurchaseResponse)
                .collect(Collectors.toList());
    }

    public List<PurchaseResponse> getAllPurchasesByProductTypeId(Long typeId, String token){

        if(productTypeClient.getProductTypeById(typeId, token) == null) throw new ResourceNotFoundException("Product type not found");

        return purchaseRepository.findPurchaseByProductTypeId(typeId)
                .stream()
                .map(this::convertToPurchaseResponse)
                .collect(Collectors.toList());
    }

    public void deletePurchase(Long id) throws ResourceNotFoundException {
        PurchaseResponse purchaseResponse = getPurchaseById(id);
        if(purchaseResponse.getId() == null) throw new ResourceNotFoundException("Purchase id cant be null");
        purchaseRepository.deleteById(id);
    }

    private PurchaseResponse convertToPurchaseResponse(Purchase purchase) {
        return new PurchaseResponse(purchase.getId(), purchase.getUserId(), purchase.getProductId(), purchase.getProductTypeId(), purchase.getQuantity());
    }
}
