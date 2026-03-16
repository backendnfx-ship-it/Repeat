package com.ultimateflange.controller;

import com.ultimateflange.dto.OrderRequest;
import com.ultimateflange.model.Order;
import com.ultimateflange.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        Map<String, Object> response = orderService.createOrder(request);

        if ((boolean) response.get("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<?> getOrdersForSupplier(@PathVariable Long supplierId) {
        try {
            List<Order> orders = orderService.getOrdersForSupplier(supplierId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", orders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<?> getOrdersForCustomer(@PathVariable String email) {
        try {
            List<Order> orders = orderService.getOrdersForCustomer(email);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", orders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}