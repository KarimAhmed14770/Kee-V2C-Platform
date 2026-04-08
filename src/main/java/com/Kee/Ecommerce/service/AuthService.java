package com.Kee.Ecommerce.service;

import com.Kee.Ecommerce.dto.*;

public interface AuthService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);
    VendorRegistrationResponse registerVendor(VendorRegistrationDto vendorRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
}
