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

    private final String adminEmail = "admin@ultimateflange.com";

    // Email to supplier when order received
    public void sendOrderToSupplier(Order order, Supplier supplier) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(supplier.getContactEmail());
        message.setSubject("🔔 NEW ORDER RECEIVED: " + order.getOrderNumber());

        String emailBody = String.format(
                "Dear %s,\n\n" +
                        "You have received a new order from a customer!\n\n" +
                        "📋 ORDER DETAILS:\n" +
                        "Order Number: %s\n" +
                        "Date: %s\n\n" +
                        "👤 CUSTOMER INFORMATION:\n" +
                        "Name: %s\n" +
                        "Company: %s\n" +
                        "Email: %s\n" +
                        "Phone: %s\n" +
                        "Address: %s\n\n" +
                        "🛒 PRODUCT DETAILS:\n" +
                        "Product: %s\n" +
                        "Quantity: %d\n" +
                        "Size: %s\n" +
                        "Material: %s\n" +
                        "Specifications: %s\n\n" +
                        "📞 Contact Method: %s\n\n" +
                        "Please login to your supplier dashboard to process this order.\n\n" +
                        "Best regards,\n" +
                        "Ultimate Flange Team",
                supplier.getContactPerson(),
                order.getOrderNumber(), order.getCreatedAt(),
                order.getCustomerName(), order.getCustomerCompany(),
                order.getCustomerEmail(), order.getCustomerPhone(),
                order.getAddress(),
                order.getProductName(), order.getQuantity(),
                order.getSize(), order.getMaterial(),
                order.getSpecifications(),
                order.getContactMethod()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }

    // Email to customer confirming order
    public void sendOrderConfirmationToCustomer(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getCustomerEmail());
        message.setSubject("✅ Order Confirmation - " + order.getOrderNumber());

        String emailBody = String.format(
                "Dear %s,\n\n" +
                        "Thank you for your order! Your order has been sent to the supplier.\n\n" +
                        "📋 ORDER SUMMARY:\n" +
                        "Order Number: %s\n" +
                        "Product: %s\n" +
                        "Quantity: %d\n" +
                        "Supplier: %s\n\n" +
                        "The supplier will contact you within 24 hours via %s.\n\n" +
                        "You can track your order status using this link:\n" +
                        "https://ultimateflange.com/track?order=%s\n\n" +
                        "Best regards,\n" +
                        "Ultimate Flange Team",
                order.getCustomerName(),
                order.getOrderNumber(),
                order.getProductName(),
                order.getQuantity(),
                order.getSupplierName(),
                order.getContactMethod(),
                order.getOrderNumber()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }

    // Email to admin for new supplier registration
    public void sendNewSupplierNotification(Supplier supplier) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(adminEmail);
        message.setSubject("🆕 New Supplier Registration: " + supplier.getCompanyName());

        String emailBody = String.format(
                "A new supplier has registered:\n\n" +
                        "Company: %s\n" +
                        "Contact Person: %s\n" +
                        "Email: %s\n" +
                        "Phone: %s\n" +
                        "Business Type: %s\n" +
                        "Location: %s\n\n" +
                        "Please verify this supplier in the admin dashboard.",
                supplier.getCompanyName(),
                supplier.getContactPerson(),
                supplier.getContactEmail(),
                supplier.getContactPhone(),
                supplier.getBusinessType(),
                supplier.getCity()
        );

        message.setText(emailBody);
        mailSender.send(message);
    }
}