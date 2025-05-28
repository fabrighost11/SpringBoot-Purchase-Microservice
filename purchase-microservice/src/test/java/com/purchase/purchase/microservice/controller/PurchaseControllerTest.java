package com.purchase.purchase.microservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purchase.purchase.microservice.api.userApi.UserClient;
import com.purchase.purchase.microservice.dto.response.ProductResponse;
import com.purchase.purchase.microservice.dto.response.ProductTypeResponse;
import com.purchase.purchase.microservice.dto.response.PurchaseResponse;
import com.purchase.purchase.microservice.dto.response.UserResponse;
import com.purchase.purchase.microservice.dto.resquest.PurchaseRequest;
import com.purchase.purchase.microservice.exception.ResourceNotFoundException;
import com.purchase.purchase.microservice.model.Purchase;
import com.purchase.purchase.microservice.repository.PurchaseRepository;
import com.purchase.purchase.microservice.service.PurchaseService;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @MockBean
    private PurchaseService purchaseService;

    private Purchase purchase;
    private PurchaseRequest purchaseRequest;
    private PurchaseResponse purchaseResponse;
    private ProductResponse productResponse;
    private ProductTypeResponse productTypeResponse;
    private UserResponse userResponse;
    private String token;


    @BeforeEach
    public void setUp() {
        token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsInN1YiI6Im1hcmlvQGdtYWlsLmNvbSIsImV4cCI6MTc0ODk0MTcxNywiaWF0IjoxNzQ4MzM2OTE3fQ.fNwkLou68G4WVqK4R4oTbTpG9CyeQEL8CypkEzOgqQs";
        purchaseRequest = new PurchaseRequest(1L,2L,3L,4);
        purchase = new Purchase(1L,1L,2L,3L,4);
        purchaseResponse = new PurchaseResponse(1L,1L,2L,3L,4);

    }

    @Test
    void getPurchaseById_findPurchaseSuccessfully_returnPurchaseResponse() throws Exception {
        Long purchaseId = 1L;
        when(purchaseService.getPurchaseById(purchaseId)).thenReturn(purchaseResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase/" + purchaseId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.productId").value(2L))
                .andExpect(jsonPath("$.productTypeId").value(3L))
                .andExpect(jsonPath("$.quantity").value(4L));
    }

    @Test
    void getPurchaseById_findPurchaseFail_returnPurchaseResponse() throws Exception {
        Long purchaseId = 77L;
        when(purchaseService.getPurchaseById(purchaseId)).thenThrow(new ResourceNotFoundException("Purchase not found"));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase/" + purchaseId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token));

        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Purchase not found"));
    }

    @Test
    void getAllPurchases_findAllPurchasesSuccessfully_returnPurchaseResponse() throws Exception {
        List<PurchaseResponse> purchaseResponseList = new ArrayList<>();
        purchaseResponseList.add(purchaseResponse);

        when(purchaseService.getAllPurchases()).thenReturn(purchaseResponseList);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].productId").value(2L))
                .andExpect(jsonPath("$[0].productTypeId").value(3L))
                .andExpect(jsonPath("$[0].quantity").value(4L));
    }

    @Test
    void createPurchase_createPurchaseSuccessfully_returnPurchaseResponse() throws Exception {

        Mockito.when(purchaseService.createPurchase(any(PurchaseRequest.class),anyString())).thenReturn(new PurchaseResponse(1L, 1L, 2L, 3L, 4));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/purchase/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseRequest))
        );
        System.out.println("JSON enviado: " + objectMapper.writeValueAsString(purchaseRequest));
        result.andDo(print());

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.productId").value(2L))
                .andExpect(jsonPath("$.productTypeId").value(3L))
                .andExpect(jsonPath("$.quantity").value(4));
    }

    @Test
    void getAllPurchasesByUserId_findAllPurchasesSuccessfully_returnPurchaseResponse() throws Exception {
        List<PurchaseResponse> purchaseResponseList = new ArrayList<>();
        purchaseResponseList.add(purchaseResponse);
        Long userId = 1L;

        Mockito.when(purchaseService.getAllPurchasesByUserId(anyLong(),anyString())).thenReturn(purchaseResponseList);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase/user/{userId}", userId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].productId").value(2L))
                .andExpect(jsonPath("$[0].productTypeId").value(3L))
                .andExpect(jsonPath("$[0].quantity").value(4L));
    }

    @Test
    void getAllPurchasesByProductTypeId_findAllPurchasesSuccessfully_returnPurchaseResponse() throws Exception {
        List<PurchaseResponse> purchaseResponseList = new ArrayList<>();
        purchaseResponseList.add(purchaseResponse);
        Long productTypeId = 3L;
        Mockito.when(purchaseService.getAllPurchasesByProductTypeId(anyLong(),anyString())).thenReturn(purchaseResponseList);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/purchase/product-type/{id}", productTypeId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].productId").value(2L))
                .andExpect(jsonPath("$[0].productTypeId").value(3L))
                .andExpect(jsonPath("$[0].quantity").value(4L));
    }

    @Test
    void deletePurchase_deletePurchaseSuccessfully_returnPurchaseResponse() throws Exception {
        Long purchaseId = 1L;

        ResultActions result = mockMvc.perform(delete("/api/purchase/{id}",purchaseId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ token));

        result.andExpect(status().isNoContent());
        verify(purchaseService).deletePurchase(purchaseId);
    }
}
