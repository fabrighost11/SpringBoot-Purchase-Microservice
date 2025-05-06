package com.purchase.purchase.microservice.controller;

import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.service.PurchaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseServiceImpl service;

    @Operation(summary = "Get purchase by ID", description = "Search a product by its ID a return it")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.getPurchaseById(id), HttpStatus.OK);
    }


    @Operation(summary = "List of products", description = "Return a list with all existing products")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "List of products not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> findAll(){
        return new ResponseEntity<>(service.getAllPurchases(), HttpStatus.OK);
    }

    @Operation(summary = "Create purchase", description = "Create a new purchase")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created."),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Mono<PurchaseResponse>> createPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest) throws MethodArgumentNotValidException {
        return new ResponseEntity<>(service.createPurchase(purchaseRequest), HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content, deleted."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Operation(summary = "Delete purchase", description = "Delete a purchase by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) throws ResourceNotFoundException {
        service.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}
