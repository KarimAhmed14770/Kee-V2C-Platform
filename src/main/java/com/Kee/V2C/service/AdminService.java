package com.Kee.V2C.service;

import com.Kee.V2C.dto.CustomerProfileResponse;
import com.Kee.V2C.dto.StatusUpdateDto;
import com.Kee.V2C.dto.VendorProfileResponse;
import com.Kee.V2C.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    CustomerProfileResponse getCustomerProfileById(Long id);
    Page<CustomerProfileResponse> searchForCustomer(String userName, String email, String firstName,
                                                    String lastName, String shippingAddress,
                                                    UserStatus status,Pageable pageable);
    VendorProfileResponse getVendorProfileById(Long id);
    public Page<VendorProfileResponse> searchForVendor(String name, String description,
                                                       String address, UserStatus status,Float lowerRating,
                                                       Float higherRating,Pageable pageable);
    Page<CustomerProfileResponse> getAllCustomers(Pageable page);
    Page<VendorProfileResponse> getAllVendors( Pageable page);

    CustomerProfileResponse  modifyCustomerStatus(Long id, StatusUpdateDto status);

    VendorProfileResponse  modifyVendorStatus(Long id,StatusUpdateDto status);


}
