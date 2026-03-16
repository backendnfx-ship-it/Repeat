package com.ultimateflange.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String companyName;

    private String gstNumber;

    private String panNumber;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String country = "India";

    private String contactPerson;

    private String contactEmail;

    private String contactPhone;

    private String website;

    private String businessType; // manufacturer, distributor, trader

    private String paymentTerms;

    private String deliveryTerms;

    private Double rating = 0.0;

    private Integer totalOrders = 0;

    private Boolean verified = false;

    private Boolean active = true;

    private String status = "ONLINE";

    private String location;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;
}