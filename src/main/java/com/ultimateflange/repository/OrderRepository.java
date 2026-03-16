package com.ultimateflange.repository;

import com.ultimateflange.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);

    List<Order> findBySupplierId(Long supplierId);

    List<Order> findBySupplierIdOrderByCreatedAtDesc(Long supplierId);

    List<Order> findByCustomerEmail(String customerEmail);

    List<Order> findByCustomerEmailOrderByCreatedAtDesc(String customerEmail);

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.supplierId = :supplierId AND o.status = :status")
    List<Order> findBySupplierIdAndStatus(@Param("supplierId") Long supplierId,
                                          @Param("status") String status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.supplierId = :supplierId AND o.status = 'PENDING'")
    Long countPendingOrdersBySupplier(@Param("supplierId") Long supplierId);

    @Query("SELECT SUM(o.amount) FROM Order o WHERE o.supplierId = :supplierId AND o.status = 'COMPLETED'")
    Double getTotalRevenueBySupplier(@Param("supplierId") Long supplierId);
}