package com.purchase.purchase.microservice.controller;

import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @Operation(summary = "Get purchase by ID", description = "Search a purchase by its ID a return it")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Purchase not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.getPurchaseById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get purchase list by ProductType", description = "Return a purchase list by its ProductType")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "ProductType not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("product-type/{id}")
    public ResponseEntity<List<PurchaseResponse>> getPurchaseByProductType(@PathVariable Long id, @RequestHeader("Authorization") String token) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.getAllPurchasesByProductTypeId(id,token), HttpStatus.OK);
    }

    @Operation(summary = "List of purchases", description = "Return a list with all existing purchases")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "List of products not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<PurchaseResponse>> findAll(){
        return new ResponseEntity<>(service.getAllPurchases(), HttpStatus.OK);
    }

    @Operation(summary = "Get a purchase list by user ID", description = "Return a purchase list by user ID")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("user/{id}")
    public ResponseEntity<List<PurchaseResponse>> getPurchaseByUserId(@PathVariable Long id, @RequestHeader("Authorization") String token) throws ResourceNotFoundException {
        return new ResponseEntity<>(service.getAllPurchasesByUserId(id,token), HttpStatus.OK);
    }

    @Operation(summary = "Create purchase", description = "Create a new purchase")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created."),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PurchaseResponse> createPurchase(@Valid @RequestBody PurchaseRequest purchaseRequest, @RequestHeader("Authorization") String token) throws MethodArgumentNotValidException {
        System.out.println("Petición recibida: " + purchaseRequest);
        return new ResponseEntity<>(service.createPurchase(purchaseRequest,token), HttpStatus.CREATED);
    }


    @Operation(summary = "Delete purchase", description = "Delete a purchase by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content, deleted."),
            @ApiResponse(responseCode = "400", description = "Bad Request."),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) throws ResourceNotFoundException {
        service.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }
}
