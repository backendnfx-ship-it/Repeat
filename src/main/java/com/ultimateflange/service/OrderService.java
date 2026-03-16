package com.ultimateflange.service;

import com.ultimateflange.dto.OrderRequest;
import com.ultimateflange.model.Order;
import com.ultimateflange.model.Supplier;
import com.ultimateflange.repository.OrderRepository;
import com.ultimateflange.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Map<String, Object> createOrder(OrderRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElse(null);

            if (supplier == null) {
                response.put("success", false);
                response.put("message", "Supplier not found");
                return response;
            }

            Order order = new Order();
            order.setOrderNumber(generateOrderNumber());

            order.setSupplierId(supplier.getId());
            order.setSupplierName(supplier.getCompanyName());
            order.setSupplierEmail(supplier.getContactEmail());
            order.setSupplierPhone(supplier.getContactPhone());

            order.setCustomerEmail(request.getEmail());
            order.setCustomerName(request.getFirstName() + " " + request.getLastName());
            order.setCustomerCompany(request.getCompany());
            order.setCustomerPhone(request.getPhone());

            order.setProductName(request.getProductName());
            order.setQuantity(request.getQuantity());
            order.setSize(request.getSize());
            order.setMaterial(request.getMaterial());
            order.setSpecifications(request.getSpecs());
            order.setAddress(request.getAddress());
            order.setContactMethod(request.getContactMethod());
            order.setAmount(request.getAmount());

            order.setStatus("PENDING");
            order.setCreatedAt(LocalDateTime.now());

            Order savedOrder = orderRepository.save(order);

            supplier.setTotalOrders(supplier.getTotalOrders() + 1);
            supplierRepository.save(supplier);

            try {
                emailService.sendOrderToSupplier(savedOrder, supplier);
                emailService.sendOrderConfirmationToCustomer(savedOrder);
            } catch (Exception e) {
                System.err.println("Email sending failed: " + e.getMessage());
            }

            response.put("success", true);
            response.put("message", "Order sent to supplier successfully");
            response.put("orderNumber", savedOrder.getOrderNumber());
            response.put("data", savedOrder);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Order failed: " + e.getMessage());
        }

        return response;
    }

    public List<Order> getOrdersForSupplier(Long supplierId) {
        return orderRepository.findBySupplierIdOrderByCreatedAtDesc(supplierId);
    }

    public List<Order> getOrdersForCustomer(String email) {
        return orderRepository.findByCustomerEmailOrderByCreatedAtDesc(email);
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000);
        return "ORD-" + timestamp + "-" + randomNum;
    }
}