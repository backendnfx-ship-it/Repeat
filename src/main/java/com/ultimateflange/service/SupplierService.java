package com.ultimateflange.service;

import com.ultimateflange.dto.SupplierDTO;
import com.ultimateflange.model.Supplier;
import com.ultimateflange.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<SupplierDTO> getAllActiveSuppliers() {
        return supplierRepository.findAllActiveVerifiedSuppliers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SupplierDTO> getSuppliersForProduct(String productKey) {
        return supplierRepository.findSuppliersForProduct(productKey)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SupplierDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        return supplier != null ? convertToDTO(supplier) : null;
    }

    public SupplierDTO getSupplierByUserId(Long userId) {
        Supplier supplier = supplierRepository.findByUserId(userId).orElse(null);
        return supplier != null ? convertToDTO(supplier) : null;
    }

    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(supplier.getId());
        dto.setUserId(supplier.getUser().getId());
        dto.setCompanyName(supplier.getCompanyName());
        dto.setContactPerson(supplier.getContactPerson());
        dto.setContactEmail(supplier.getContactEmail());
        dto.setContactPhone(supplier.getContactPhone());
        dto.setAddress(supplier.getAddress());
        dto.setCity(supplier.getCity());
        dto.setState(supplier.getState());
        dto.setPincode(supplier.getPincode());
        dto.setBusinessType(supplier.getBusinessType());
        dto.setRating(supplier.getRating());
        dto.setTotalOrders(supplier.getTotalOrders());
        dto.setVerified(supplier.getVerified());
        dto.setActive(supplier.getActive());
        dto.setStatus(supplier.getStatus());
        dto.setLocation(supplier.getLocation());
        dto.setPaymentTerms(supplier.getPaymentTerms());
        dto.setDeliveryTerms(supplier.getDeliveryTerms());
        return dto;
    }
}