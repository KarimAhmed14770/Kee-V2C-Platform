package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.CustomerProfileResponse;
import com.Kee.Ecommerce.dto.CustomerRegistrationResponse;
import com.Kee.Ecommerce.dto.VendorProfileResponse;
import com.Kee.Ecommerce.enums.UserRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    CustomerProfileResponse getCustomerById(Long id);
    CustomerProfileResponse getCustomerByUsername(String userName);
    CustomerProfileResponse getCustomerByEmail(String email);
    VendorProfileResponse getVendorById(Long id);
    VendorProfileResponse getVendorByUsername(String userName);
    VendorProfileResponse getVendorByEmail(String email);
    Page<CustomerProfileResponse> getAllCustomers(Pageable page);
    Page<VendorProfileResponse> getAllVendors( Pageable page);

}
