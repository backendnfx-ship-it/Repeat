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
    private String orderNumber;  // ✅ getOrderRef() nahi, getOrderNumber() hai

    // Supplier Info
    @Column(nullable = false)
    private Long supplierId;
    private String supplierName;
    private String supplierEmail;
    private String supplierPhone;

    // Customer Info
    private Long customerId;
    @Column(nullable = false)
    private String customerEmail;
    private String customerName;
    private String customerCompany;
    private String customerPhone;

    // Product Info
    private String productKey;
    private String productName;
    private Integer quantity;
    private String size;
    private String material;
    @Column(length = 1000)
    private String specifications;
    private String address;
    private String contactMethod;
    private Double amount;

    private String status = "PENDING";
    private LocalDateTime processingDate;
    private LocalDateTime shippingDate;
    private LocalDateTime completedDate;

    @CreationTimestamp
    private LocalDateTime createdAt;  // ✅ getOrderDate() nahi, getCreatedAt() hai

    // Helper methods
    public String getOrderRef() {
        return this.orderNumber;  // ✅ Agar getOrderRef() chahiye to ye method add karo
    }

    public LocalDateTime getOrderDate() {
        return this.createdAt;  // ✅ Agar getOrderDate() chahiye to ye method add karo
    }
}