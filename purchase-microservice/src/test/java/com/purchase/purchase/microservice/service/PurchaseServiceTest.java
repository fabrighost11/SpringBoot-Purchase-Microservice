package com.purchase.purchase.microservice.service;

import com.purchase.purchase.microservice.api.productApi.ProductClient;
import com.purchase.purchase.microservice.api.productTypeApi.ProductTypeClient;
import com.purchase.purchase.microservice.api.userApi.UserClient;
import com.purchase.purchase.microservice.dto.response.ProductResponse;
import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.response.UserResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.model.Purchase;
import com.purchase.purchase.microservice.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductClient productClient;

    @Mock
    private ProductTypeClient productTypeClient;

    @Mock
    private UserClient userClient;

    @InjectMocks
    @Spy
    private PurchaseService purchaseService;

    private Purchase purchase;
    private PurchaseRequest purchaseRequest;
    private PurchaseResponse purchaseResponse;
    private ProductResponse productResponse;
    private ProductTypeResponse productTypeResponse;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {

        purchase = new Purchase();
        purchase.setId(1L);
        purchase.setProductId(1L);
        purchase.setUserId(1L);
        purchase.setProductTypeId(1L);
        purchase.setQuantity(4);

        purchaseRequest = new PurchaseRequest();
        purchaseRequest.setProductId(1L);
        purchaseRequest.setUserId(1L);
        purchaseRequest.setProductTypeId(1L);
        purchaseRequest.setQuantity(4);

        purchaseResponse = new PurchaseResponse();
        purchaseResponse.setId(1L);
        purchaseResponse.setProductId(1L);
        purchaseResponse.setUserId(1L);
        purchaseResponse.setProductTypeId(1L);
        purchaseResponse.setQuantity(4);

        productResponse = new ProductResponse();
        productResponse.setId(1L);
        productResponse.setName("Product");
        productResponse.setPrice(22);
        productResponse.setStock(12);
        productResponse.setProductTypeId(1L);
        productResponse.setProductTypeName("New Product Type");

        productTypeResponse = new ProductTypeResponse();
        productTypeResponse.setId(1L);
        productTypeResponse.setName("New Product Type");

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setName("User1");
        userResponse.setEmail("User1@gmail.com");
        userResponse.setRole("DEFAULT_USER");

    }

    @Test
    void getPurchaseById_findPurchaseSuccessfully_returnsPurchaseResponse() throws Exception {
        PurchaseResponse expected = purchaseResponse;
        Long purchaseId = 1L;
        when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));

        PurchaseResponse actual = purchaseService.getPurchaseById(purchaseId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getPurchaseById_purchaseNotFound_returnsPurchaseResponse() {
        Long nonExistentId = 1L;
        String expected = "Purchase not found";
        when(purchaseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResourceNotFoundException actualException = assertThrows(
                ResourceNotFoundException.class,
                () -> purchaseService.deletePurchase(nonExistentId)
        );
        Assertions.assertEquals(expected, actualException.getMessage());
    }

    @Test
    void getAllPurchases_findAllPurchasesSuccessfully_returnsPurchaseResponseList() throws Exception {
        List<PurchaseResponse> expected = List.of(purchaseResponse);
        when(purchaseRepository.findAll()).thenReturn(List.of(purchase));

        List<PurchaseResponse> actual = purchaseService.getAllPurchases();

        Assertions.assertEquals(expected, actual);

    }

    @Test
    void deletePurchase_deletePurchaseSuccessfully_returnsPurchaseResponse() throws Exception {
        Long purchaseId = 1L;
        purchaseResponse.setId(1L);
        doReturn(purchaseResponse).when(purchaseService).getPurchaseById(purchaseId);
        purchaseService.deletePurchase(purchaseId);

        verify(purchaseRepository, times(1)).deleteById(purchaseId);
    }

    @Test
    void deletePurchase_purchaseNotFound_returnsPurchaseResponse() {
        Long purchaseId = 1L;
        String expected = "Purchase id cant be null";

        purchaseResponse.setId(null);

        doReturn(purchaseResponse).when(purchaseService).getPurchaseById(purchaseId);

        ResourceNotFoundException actual = assertThrows(
                ResourceNotFoundException.class,
                () -> purchaseService.deletePurchase(purchaseId)
        );

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void createPurchase_createPurchaseSuccessfully_returnsPurchaseResponse() throws Exception {
        PurchaseResponse expected = purchaseResponse;

        when(productClient.getProductById(purchaseRequest.getProductId())).thenReturn(productResponse);
        when(productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId())).thenReturn(productTypeResponse);
        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(userResponse);
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

        PurchaseResponse actual = purchaseService.createPurchase(purchaseRequest);

        Assertions.assertEquals(expected, actual);
        Assertions.assertNotNull(purchaseResponse);

    }

    @Test
    void createPurchase_productTypeIdDoesntMatchWithProductTypeId_returnsException() {
        String expected = "Product type does not match with the current product";
        purchaseRequest.setProductTypeId(3L);
        productResponse.setProductTypeId(1L);
        productTypeResponse.setId(3L);

        when(productClient.getProductById(purchaseRequest.getProductId())).thenReturn(productResponse);
        when(productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId())).thenReturn(productTypeResponse);
        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(userResponse);

        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.createPurchase(purchaseRequest);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void createPurchase_insufficientStockForQuantity_returnsException() {
        String expected = "Insufficient stock";
        purchaseRequest.setQuantity(77);
        productResponse.setStock(3);

        when(productClient.getProductById(purchaseRequest.getProductId())).thenReturn(productResponse);
        when(productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId())).thenReturn(productTypeResponse);
        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(userResponse);

        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.createPurchase(purchaseRequest);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void createPurchase_nullUserId_returnsException() {
        String expected = "User not found";
        purchaseRequest.setUserId(null);

        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(null);
        ResourceNotFoundException actual = assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.createPurchase(purchaseRequest);
        });
        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void createPurchase_nullProductId_returnsException() {
        String expected = "Product not found";
        purchaseRequest.setProductId(null);

        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(userResponse);
        when(productClient.getProductById(purchaseRequest.getProductId())).thenReturn(null);
        ResourceNotFoundException actual = assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.createPurchase(purchaseRequest);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void createPurchase_nullProductTypeId_returnsException() {
        String expected = "Product type not found";
        purchaseRequest.setProductTypeId(null);

        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(userResponse);
        when(productClient.getProductById(purchaseRequest.getProductId())).thenReturn(productResponse);
        when(productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId())).thenReturn(null);
        ResourceNotFoundException actual = assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.createPurchase(purchaseRequest);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void getAllPurchasesByUserId_findAllPurchasesSuccessfully_returnsPurchaseResponseList() throws Exception {
        List<PurchaseResponse> expected = List.of(purchaseResponse);

        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(userResponse);
        when(purchaseRepository.findPurchaseByUserId(purchaseRequest.getUserId())).thenReturn(List.of(purchase));
        List<PurchaseResponse> actual = purchaseService.getAllPurchasesByUserId(purchaseRequest.getUserId());
        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());
    }

    @Test
    void getAllPurchasesByUserId_userIdNull_returnsException() {
        String expected = "User not found";
        purchaseRequest.setUserId(null);

        when(userClient.getUserById(purchaseRequest.getUserId())).thenReturn(null);

        ResourceNotFoundException actual = assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.getAllPurchasesByUserId(purchaseRequest.getUserId());
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    @Test
    void getAllPurchasesByProductTypeId_getPurchasesSuccessfully_returnPurchaseResponse() throws Exception {
        List<PurchaseResponse> expected = List.of(purchaseResponse);

        when(productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId())).thenReturn(productTypeResponse);
        when(purchaseRepository.findPurchaseByProductTypeId(purchaseRequest.getProductTypeId())).thenReturn(List.of(purchase));

        List<PurchaseResponse> actual = purchaseService.getAllPurchasesByProductTypeId(purchaseRequest.getProductTypeId());

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.get(0).getProductId(), actual.get(0).getProductId());
        Assertions.assertEquals(expected.get(0).getProductTypeId(), actual.get(0).getProductTypeId());

    }

    @Test
    void getAllPurchasesByProductTypeId_productTypeNull_returnsException() {
        String expected = "Product type not found";
        purchaseRequest.setProductTypeId(null);

        when(productTypeClient.getProductTypeById(purchaseRequest.getProductTypeId())).thenReturn(null);

        ResourceNotFoundException actual = assertThrows(ResourceNotFoundException.class, () -> {
            purchaseService.getAllPurchasesByProductTypeId(purchaseRequest.getProductTypeId());
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }
}
