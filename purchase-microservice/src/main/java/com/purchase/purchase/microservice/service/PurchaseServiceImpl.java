package com.purchase.purchase.microservice.service;

import com.purchase.purchase.microservice.dto.response.ProductResponse;
import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.response.UserResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.model.Purchase;
import com.purchase.purchase.microservice.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements IPurchaseService{


    private final PurchaseRepository purchaseRepository;

    private final WebClient productClient;

    private final WebClient userClient;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, WebClient productClient, WebClient userClient) {
        this.purchaseRepository = purchaseRepository;
        this.productClient = productClient;
        this.userClient = userClient;
    }

    @Override
    public PurchaseResponse getPurchaseById(Long id) throws ResourceNotFoundException {
         Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found"));
         return convertToPurchaseResponse(purchase);
    }

    @Override
    public List<PurchaseResponse> getAllPurchases(){
        return purchaseRepository.findAll()
                .stream()
                .map(this::convertToPurchaseResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Mono<PurchaseResponse> createPurchase(PurchaseRequest purchaseRequest){
        Mono<ProductResponse> productMono = productClient.get()
                .uri("/" + purchaseRequest.getProductId())
                .retrieve()
                .bodyToMono(ProductResponse.class);

        Mono<UserResponse> userMono = userClient.get()
                .uri("/" + purchaseRequest.getUserId())
                .retrieve()
                .bodyToMono(UserResponse.class);

        return Mono.zip(productMono, userMono)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(tuple -> {
                    ProductResponse product = tuple.getT1();
                    UserResponse user = tuple.getT2();

                    if (product.getStock() < purchaseRequest.getQuantity()){
                        return Mono.error(new IllegalArgumentException("Cant purchase a higher quantity than the existing product stock."));
                    }

                    if(product.getStock() == 0 || product.getStock() == null){
                        return Mono.error(new IllegalArgumentException("Cannot progress with the purchase, there's not stock of this product."));
                    }

                    Purchase purchase = new Purchase();
                    purchase.setProductId(product.getId());
                    purchase.setUserId(user.getId());
                    purchase.setQuantity(purchaseRequest.getQuantity());
                    Integer newStock = product.getStock() - purchaseRequest.getQuantity();

                    Purchase created = purchaseRepository.save(purchase);
                    return Mono.just(convertToPurchaseResponse(created));
                });
    }

    @Override
    public void deletePurchase(Long id) throws ResourceNotFoundException{

        if(!purchaseRepository.existsById(id)) throw new ResourceNotFoundException("Purchase not found");

        purchaseRepository.deleteById(id);
    }

    private PurchaseResponse convertToPurchaseResponse(Purchase purchase) {
        return new PurchaseResponse(purchase.getId(), purchase.getUserId(), purchase.getProductId(), purchase.getQuantity());
    }
}
