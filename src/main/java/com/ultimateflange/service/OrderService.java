package com.ultimateflange.service;

import com.ultimateflange.dto.OrderRequest;
import com.ultimateflange.dto.OrderResponse;
import com.ultimateflange.model.Order;
import com.ultimateflange.model.Supplier;
import com.ultimateflange.model.User;
import com.ultimateflange.repository.OrderRepository;
import com.ultimateflange.repository.SupplierRepository;
import com.ultimateflange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        OrderResponse response = new OrderResponse();

        try {
            // Validate supplier
            if (request.getSupplierId() == null) {
                response.setSuccess(false);
                response.setMessage("Please select a supplier");
                return response;
            }

            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                    .orElse(null);

            if (supplier == null) {
                response.setSuccess(false);
                response.setMessage("Selected supplier not found");
                return response;
            }

            // Create order
            Order order = new Order();
            order.setOrderNumber(generateOrderNumber());

            // Supplier info
            order.setSupplierId(supplier.getId());
            order.setSupplierName(supplier.getCompanyName());
            order.setSupplierEmail(supplier.getContactEmail());
            order.setSupplierPhone(supplier.getContactPhone());

            // Customer info
            order.setCustomerId(request.getUserId());
            order.setCustomerEmail(request.getEmail());
            order.setCustomerName(request.getFirstName() + " " + request.getLastName());
            order.setCustomerCompany(request.getCompany());
            order.setCustomerPhone(request.getPhone());

            // Product info
            order.setProductKey(request.getProductKey() != null ? request.getProductKey() :
                    request.getProduct().toLowerCase().replace(" ", "-"));
            order.setProductName(request.getProductName() != null ? request.getProductName() :
                    request.getProduct());
            order.setQuantity(request.getQuantity());
            order.setSize(request.getSize());
            order.setMaterial(request.getMaterial());
            order.setSpecifications(request.getSpecifications());
            order.setAddress(request.getAddress());
            order.setContactMethod(request.getContactMethod());
            order.setAmount(request.getAmount());

            order.setStatus("PENDING");
            order.setCreatedAt(LocalDateTime.now());

            Order savedOrder = orderRepository.save(order);

            // Update supplier order count
            supplier.setTotalOrders(supplier.getTotalOrders() + 1);
            supplierRepository.save(supplier);

            // Send emails
            try {
                // Email to supplier
                emailService.sendOrderToSupplier(savedOrder, supplier);

                // Email to customer
                emailService.sendOrderConfirmationToCustomer(savedOrder);
            } catch (Exception e) {
                System.err.println("Email sending failed: " + e.getMessage());
            }

            // Prepare response
            response.setSuccess(true);
            response.setMessage("Order sent to supplier successfully");
            response.setOrderNumber(savedOrder.getOrderNumber());
            response.setId(savedOrder.getId());
            response.setProductName(savedOrder.getProductName());
            response.setQuantity(savedOrder.getQuantity());
            response.setAmount(savedOrder.getAmount());
            response.setStatus(savedOrder.getStatus());
            response.setCreatedAt(savedOrder.getCreatedAt());

            response.setCustomerName(savedOrder.getCustomerName());
            response.setCustomerEmail(savedOrder.getCustomerEmail());

            response.setSupplierId(savedOrder.getSupplierId());
            response.setSupplierName(savedOrder.getSupplierName());
            response.setSupplierEmail(savedOrder.getSupplierEmail());
            response.setSupplierPhone(savedOrder.getSupplierPhone());

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Order creation failed: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    // Get orders for a specific supplier
    public List<OrderResponse> getOrdersForSupplier(Long supplierId) {
        List<Order> orders = orderRepository.findBySupplierIdOrderByCreatedAtDesc(supplierId);

        return orders.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    // Get orders for a specific customer
    public List<OrderResponse> getOrdersForCustomer(String customerEmail) {
        List<Order> orders = orderRepository.findByCustomerEmailOrderByCreatedAtDesc(customerEmail);

        return orders.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    // Get order by ID
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;

        return convertToResponse(order);
    }

    // Get order by order number
    public OrderResponse getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        if (order == null) return null;

        return convertToResponse(order);
    }

    // Update order status
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        OrderResponse response = new OrderResponse();

        if (order == null) {
            response.setSuccess(false);
            response.setMessage("Order not found");
            return response;
        }

        order.setStatus(status);

        // Update timestamps based on status
        if ("PROCESSING".equalsIgnoreCase(status)) {
            order.setProcessingDate(LocalDateTime.now());
        } else if ("SHIPPED".equalsIgnoreCase(status)) {
            order.setShippingDate(LocalDateTime.now());
        } else if ("COMPLETED".equalsIgnoreCase(status)) {
            order.setCompletedDate(LocalDateTime.now());
        }

        orderRepository.save(order);

        response.setSuccess(true);
        response.setMessage("Order status updated to " + status);
        response.setStatus(status);

        return response;
    }

    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setProductName(order.getProductName());
        response.setQuantity(order.getQuantity());
        response.setSize(order.getSize());
        response.setMaterial(order.getMaterial());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());

        response.setCustomerName(order.getCustomerName());
        response.setCustomerEmail(order.getCustomerEmail());
        response.setCustomerCompany(order.getCustomerCompany());

        response.setSupplierId(order.getSupplierId());
        response.setSupplierName(order.getSupplierName());
        response.setSupplierEmail(order.getSupplierEmail());
        response.setSupplierPhone(order.getSupplierPhone());

        return response;
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000);
        return "ORD-" + timestamp + "-" + randomNum;
    }
}