package com.Kee.V2C.service.Authentication;

import com.Kee.V2C.dto.Authentication.AuthenticationResponse;
import com.Kee.V2C.dto.Authentication.LoginRequest;
import com.Kee.V2C.dto.customer.CustomerRegistrationDTO;
import com.Kee.V2C.dto.customer.CustomerRegistrationResponse;
import com.Kee.V2C.dto.vendor.VendorRegistrationDto;
import com.Kee.V2C.dto.vendor.VendorRegistrationResponse;

public interface AuthService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);
    VendorRegistrationResponse registerVendor(VendorRegistrationDto vendorRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
}
