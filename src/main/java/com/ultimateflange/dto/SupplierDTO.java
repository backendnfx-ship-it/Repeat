package com.ultimateflange.dto;

import lombok.Data;

@Data
public class SupplierDTO {
    private Long id;
    private Long userId;
    private String companyName;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String businessType;
    private Double rating;
    private Integer totalOrders;
    private Boolean verified;
    private Boolean active;
    private String status;
    private String location;
    private String paymentTerms;
    private String deliveryTerms;
}