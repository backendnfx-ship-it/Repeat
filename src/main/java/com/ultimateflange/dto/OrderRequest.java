package com.ultimateflange.dto;

import lombok.Data;

@Data  // ✅ Lombok auto-generates getters
public class OrderRequest {
    private Long supplierId;
    private String supplierName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String productName;
    private Integer quantity;
    private String size;
    private String material;
    private String specs;
    private String address;
    private String contactMethod;
    private Double amount;

    // Agar lombok nahi use kar rahe to ye getters add karo:
    /*
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCompany() { return company; }
    public String getPhone() { return phone; }
    */
}