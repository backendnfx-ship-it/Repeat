package com.ultimateflange.controller;

import com.ultimateflange.dto.SupplierDTO;
import com.ultimateflange.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suppliers")
@CrossOrigin(origins = "*")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> getAllSuppliers() {
        try {
            List<SupplierDTO> suppliers = supplierService.getAllActiveSuppliers();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", suppliers);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/product/{productKey}")
    public ResponseEntity<?> getSuppliersForProduct(@PathVariable String productKey) {
        try {
            List<SupplierDTO> suppliers = supplierService.getSuppliersForProduct(productKey);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", suppliers);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable Long id) {
        try {
            SupplierDTO supplier = supplierService.getSupplierById(id);

            if (supplier == null) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Supplier not found");
                return ResponseEntity.notFound().build();
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", supplier);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSupplierByUserId(@PathVariable Long userId) {
        try {
            SupplierDTO supplier = supplierService.getSupplierByUserId(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", supplier);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}