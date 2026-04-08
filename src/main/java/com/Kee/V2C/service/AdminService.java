package com.Kee.V2C.service;

import com.Kee.V2C.dto.CustomerProfileResponse;
import com.Kee.V2C.dto.VendorProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    CustomerProfileResponse getCustomerById(Long id);
    CustomerProfileResponse getCustomerByUsername(String userName);
    CustomerProfileResponse getCustomerByEmail(String email);
    Page<CustomerProfileResponse> searchForCustomer(String userName,String email,String firstName,
                                                    String lastName,String shippingAddress,Boolean status);
    VendorProfileResponse getVendorById(Long id);
    VendorProfileResponse getVendorByUsername(String userName);
    VendorProfileResponse getVendorByEmail(String email);
    Page<CustomerProfileResponse> getAllCustomers(Pageable page);
    Page<VendorProfileResponse> getAllVendors( Pageable page);

}
