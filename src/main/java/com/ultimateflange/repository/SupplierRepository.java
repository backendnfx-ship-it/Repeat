
package com.ultimateflange.repository;

import com.ultimateflange.model.Supplier;
import com.ultimateflange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByUser(User user);
    Optional<Supplier> findByUserId(Long userId);
    List<Supplier> findByActiveTrue();

    @Query("SELECT s FROM Supplier s WHERE s.active = true")
    List<Supplier> findAllActiveSuppliers();

    @Query(value = "SELECT s.* FROM suppliers s WHERE s.active = true", nativeQuery = true)
    List<Supplier> getActiveSuppliers();
}