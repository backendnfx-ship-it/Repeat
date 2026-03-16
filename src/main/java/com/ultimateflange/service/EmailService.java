package com.ultimateflange.service;

import com.ultimateflange.model.Order;
import com.ultimateflange.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    // ✅ sendOrderToSupplier method
    public void sendOrderToSupplier(Order order, Supplier supplier) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(supplier.getContactEmail());
            message.setSubject("🔔 New Order: " + order.getOrderNumber());

            String emailBody = String.format(
                    "Dear %s,\n\n" +
                            "You have received a new order!\n\n" +
                            "Order Number: %s\n" +
                            "Customer: %s\n" +
                            "Product: %s\n" +
                            "Quantity: %d\n\n" +
                            "Please login to your dashboard to view details.",
                    supplier.getContactPerson(),
                    order.getOrderNumber(),
                    order.getCustomerName(),
                    order.getProductName(),
                    order.getQuantity()
            );

            message.setText(emailBody);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to supplier: " + e.getMessage());
        }
    }

    // ✅ sendOrderConfirmationToCustomer method
    public void sendOrderConfirmationToCustomer(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(order.getCustomerEmail());
            message.setSubject("✅ Order Confirmation: " + order.getOrderNumber());

            String emailBody = String.format(
                    "Dear %s,\n\n" +
                            "Thank you for your order!\n\n" +
                            "Order Number: %s\n" +
                            "Supplier: %s\n" +
                            "Product: %s\n" +
                            "Quantity: %d\n\n" +
                            "The supplier will contact you within 24 hours.",
                    order.getCustomerName(),
                    order.getOrderNumber(),
                    order.getSupplierName(),
                    order.getProductName(),
                    order.getQuantity()
            );

            message.setText(emailBody);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to customer: " + e.getMessage());
        }
    }

    // ✅ Text Block version (Java 15+)
    public void sendOrderNotification(Order order) {
        String emailBody = """
                Dear Customer,
                
                Your order has been placed successfully.
                
                Order Number: %s
                Product: %s
                Quantity: %d
                
                Thank you for choosing us!
                """.formatted(order.getOrderNumber(), order.getProductName(), order.getQuantity());

        // Send email logic here
    }
}