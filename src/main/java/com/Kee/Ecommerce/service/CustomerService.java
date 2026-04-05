package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.*;

public interface CustomerService {
    CustomerProfileResponse updateCustomerProfile(CustomerProfileRequest customerProfileRequest);
    CustomerProfileResponse myProfile();
}
