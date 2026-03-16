package com.ultimateflange.controller;

import com.ultimateflange.model.Supplier;
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
            List<Supplier> suppliers = supplierService.getAllActiveSuppliers();

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
}