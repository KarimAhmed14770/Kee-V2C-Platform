package com.Kee.Ecommerce.rest;

import com.Kee.Ecommerce.dto.CustomerProfileRequest;
import com.Kee.Ecommerce.dto.CustomerProfileResponse;
import com.Kee.Ecommerce.dto.SellerProfileRequest;
import com.Kee.Ecommerce.dto.SellerProfileResponse;
import com.Kee.Ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService){
        this.customerService=customerService;
    }
    @PutMapping("/update")
    public ResponseEntity<CustomerProfileResponse> updateSeller(@RequestBody CustomerProfileRequest customerProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomerProfile(customerProfileRequest));
    }
}
