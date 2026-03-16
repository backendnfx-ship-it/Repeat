package com.ultimateflange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String productName;
    private Integer quantity;
    private String size;
    private String material;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;

    // Customer info
    private String customerName;
    private String customerEmail;
    private String customerCompany;

    // Supplier info
    private Long supplierId;
    private String supplierName;
    private String supplierEmail;
    private String supplierPhone;

    private boolean success;
    private String message;
}