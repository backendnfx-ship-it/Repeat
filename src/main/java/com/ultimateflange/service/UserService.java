package com.ultimateflange.service;

import com.ultimateflange.dto.LoginRequest;
import com.ultimateflange.dto.SignupRequest;
import com.ultimateflange.dto.AuthResponse;
import com.ultimateflange.model.User;
import com.ultimateflange.model.Supplier;
import com.ultimateflange.repository.UserRepository;
import com.ultimateflange.repository.SupplierRepository;
import com.ultimateflange.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        // Check if user exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, null, null, null, null, null,
                    "Email already exists", false);
        }

        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCompany(request.getCompany());
        user.setPhone(request.getPhone());
        user.setIndustry(request.getIndustry());
        user.setUserType(request.getUserType() != null ? request.getUserType() : "partner");
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        // If user type is supplier, create supplier profile
        if ("supplier".equalsIgnoreCase(request.getUserType())) {
            createSupplierProfile(savedUser, request);
        }

        // Generate token
        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId(),
                savedUser.getUserType());

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUserType(),
                token,
                "Signup successful",
                true
        );
    }

    public AuthResponse login(LoginRequest request) {
        // Find user by email - using userRepository
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(null, null, null, null, null, null,
                    "Invalid email or password", false);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getUserType());

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserType(),
                token,
                "Login successful",
                true
        );
    }

    private void createSupplierProfile(User user, SignupRequest request) {
        Supplier supplier = new Supplier();
        supplier.setUser(user);
        supplier.setCompanyName(request.getCompany() != null ? request.getCompany() : user.getCompany());
        supplier.setContactPerson(user.getFirstName() + " " + user.getLastName());
        supplier.setContactEmail(user.getEmail());
        supplier.setContactPhone(user.getPhone());
        supplier.setGstNumber(request.getGstNumber());
        supplier.setPanNumber(request.getPanNumber());
        supplier.setAddress(request.getAddress());
        supplier.setCity(request.getCity());
        supplier.setState(request.getState());
        supplier.setPincode(request.getPincode());
        supplier.setBusinessType(request.getBusinessType());
        supplier.setLocation(request.getCity() != null ? request.getCity() : "India");
        supplier.setVerified(false);
        supplier.setActive(true);
        supplier.setStatus("ONLINE");
        supplier.setRating(0.0);
        supplier.setTotalOrders(0);

        supplierRepository.save(supplier);
    }
}