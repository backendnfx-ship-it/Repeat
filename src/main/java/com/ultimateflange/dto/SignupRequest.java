package com.ultimateflange.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String company;
    private String phone;
    private String industry;
    private String userType; // "partner" ya "supplier"

    // Supplier specific fields (optional)
    private String gstNumber;
    private String panNumber;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String businessType;
}