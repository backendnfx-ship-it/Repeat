package com.ultimateflange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AuthResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String userType;
    private String token;
    private String message;
    private boolean success;

    // ✅ Constructor for error responses
    public AuthResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // ✅ Constructor with all fields
    public AuthResponse(Long id, String email, String firstName, String lastName,
                        String userType, String token, String message, boolean success) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.token = token;
        this.message = message;
        this.success = success;
    }

    // ✅ No builder method - use constructors directly
}