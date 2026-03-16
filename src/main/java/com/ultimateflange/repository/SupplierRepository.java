package com.ultimateflange.repository;

import com.ultimateflange.model.Supplier;
import com.ultimateflange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByUser(User user);

    Optional<Supplier> findByUserId(Long userId);

    Optional<Supplier> findByContactEmail(String email);

    List<Supplier> findByActiveTrue();

    List<Supplier> findByVerifiedTrue();

    List<Supplier> findByCityAndActiveTrue(String city);

    List<Supplier> findByStateAndActiveTrue(String state);

    @Query("SELECT s FROM Supplier s WHERE s.active = true AND s.verified = true")
    List<Supplier> findAllActiveVerifiedSuppliers();

    @Query("SELECT s FROM Supplier s WHERE LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.businessType) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Supplier> searchSuppliers(@Param("keyword") String keyword);

    @Query(value = "SELECT s.* FROM suppliers s " +
            "JOIN products p ON p.supplier_id = s.id " +
            "WHERE p.product_key = :productKey AND s.active = true AND s.verified = true " +
            "GROUP BY s.id", nativeQuery = true)
    List<Supplier> findSuppliersForProduct(@Param("productKey") String productKey);
}