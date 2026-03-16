package com.ultimateflange.repository;

import com.ultimateflange.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);
    List<Order> findBySupplierIdOrderByCreatedAtDesc(Long supplierId);
    List<Order> findByCustomerEmailOrderByCreatedAtDesc(String customerEmail);
    List<Order> findBySupplierIdAndStatus(Long supplierId, String status);
}