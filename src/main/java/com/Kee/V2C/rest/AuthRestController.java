package com.Kee.V2C.rest;


import com.Kee.V2C.dto.*;
import com.Kee.V2C.service.AuthService;
import com.Kee.V2C.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final CustomerService customerService;
    private final AuthService authService;

    @Autowired
    public AuthRestController(CustomerService customerService,AuthService authService){
        this.customerService = customerService;
        this.authService=authService;
    }

    @PostMapping("/register/customer")
    public ResponseEntity<CustomerRegistrationResponse> register(@RequestBody CustomerRegistrationDTO customerRegistrationDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerCustomer(customerRegistrationDTO));
    }
    @PostMapping("/register/vendor")
    public ResponseEntity<VendorRegistrationResponse> register(@RequestBody VendorRegistrationDto vendorRegistrationDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerVendor(vendorRegistrationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginProcess(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.logIn(loginRequest));
        }

}

