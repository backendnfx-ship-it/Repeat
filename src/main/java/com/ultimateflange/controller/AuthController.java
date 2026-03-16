package com.ultimateflange.controller;

import com.ultimateflange.dto.LoginRequest;
import com.ultimateflange.dto.SignupRequest;
import com.ultimateflange.dto.AuthResponse;
import com.ultimateflange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            AuthResponse response = userService.signup(request);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, null, null, null, null,
                            "Error: " + e.getMessage(), false)
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = userService.login(request);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new AuthResponse(null, null, null, null, null, null,
                            "Error: " + e.getMessage(), false)
            );
        }
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Auth controller is working!");
    }
}