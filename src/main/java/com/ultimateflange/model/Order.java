package com.ultimateflange.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    // Supplier Information
    @Column(nullable = false)
    private Long supplierId;

    private String supplierName;

    private String supplierEmail;

    private String supplierPhone;

    // Customer Information
    private Long customerId;

    @Column(nullable = false)
    private String customerEmail;

    private String customerName;

    private String customerCompany;

    private String customerPhone;

    // Product Information
    @Column(nullable = false)
    private String productKey;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String material;

    @Column(length = 1000)
    private String specifications;

    private String address;

    private String contactMethod;

    private Double amount;

    private String status = "PENDING"; // PENDING, PROCESSING, SHIPPED, COMPLETED, CANCELLED

    private LocalDateTime processingDate;

    private LocalDateTime shippingDate;  // ✅ Fixed: was LocalShippingDate

    private LocalDateTime completedDate;

    private String trackingNumber;

    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;
}