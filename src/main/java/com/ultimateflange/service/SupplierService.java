package com.ultimateflange.service;

import com.ultimateflange.model.Supplier;
import com.ultimateflange.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllActiveSuppliers() {
        return supplierRepository.findAllActiveSuppliers();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public Supplier getSupplierByUserId(Long userId) {
        return supplierRepository.findByUserId(userId).orElse(null);
    }

    public Map<String, Object> getSuppliersForProduct(String productKey) {
        Map<String, Object> response = new HashMap<>();

        List<Supplier> suppliers = supplierRepository.getActiveSuppliers();

        // Add rating or other logic if needed
        suppliers.forEach(supplier -> {
            if (supplier.getRating() == null) {
                supplier.setRating(4.5); // Default rating
            }
        });

        response.put("success", true);
        response.put("data", suppliers);
        response.put("count", suppliers.size());

        return response;
    }

    public Map<String, Object> updateSupplierStatus(Long supplierId, String status) {
        Map<String, Object> response = new HashMap<>();

        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        if (supplier == null) {
            response.put("success", false);
            response.put("message", "Supplier not found");
            return response;
        }

        supplier.setStatus(status);
        supplierRepository.save(supplier);

        response.put("success", true);
        response.put("message", "Supplier status updated to " + status);
        response.put("data", supplier);

        return response;
    }

    public Map<String, Object> verifySupplier(Long supplierId) {
        Map<String, Object> response = new HashMap<>();

        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        if (supplier == null) {
            response.put("success", false);
            response.put("message", "Supplier not found");
            return response;
        }

        supplier.setVerified(true);
        supplierRepository.save(supplier);

        response.put("success", true);
        response.put("message", "Supplier verified successfully");

        return response;
    }

    public Long getTotalSuppliersCount() {
        return supplierRepository.count();
    }

    public List<Supplier> searchSuppliers(String keyword) {
        // This would need a custom query in repository
        // For now, return all active suppliers
        return supplierRepository.findAllActiveSuppliers();
    }
}