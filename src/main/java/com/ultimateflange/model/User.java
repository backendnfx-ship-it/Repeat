package com.ultimateflange.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String company;

    private String phone;

    private String industry;

    @Column(nullable = false)
    private String userType; // "partner" or "supplier"

    @Column(nullable = false)
    private String role = "USER";

    @CreationTimestamp
    private LocalDateTime createdAt;
}