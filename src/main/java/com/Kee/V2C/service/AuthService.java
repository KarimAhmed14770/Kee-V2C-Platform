package com.Kee.V2C.service;

import com.Kee.V2C.dto.*;

public interface AuthService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);
    VendorRegistrationResponse registerVendor(VendorRegistrationDto vendorRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
}
