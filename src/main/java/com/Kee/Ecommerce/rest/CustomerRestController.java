package com.Kee.Ecommerce.rest;

import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {
    private final UserService userService;

    @Autowired
    public CustomerRestController(UserService userService){
        this.userService=userService;
    }
    @PutMapping("/update")
    public ResponseEntity<UserProfileResponse> updateSeller(@RequestBody UserProfileRequest userProfileRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateCustomerProfile(userProfileRequest));
    }

    @GetMapping("/my-profile")
    public ResponseEntity<UserProfileResponse> myProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.myProfile());
    }

    @PostMapping("/cart")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartItemRequest cartItemRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.addToCart(cartItemRequest));
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponse> viewMyCart(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.viewMyCart());
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckOutRequest checkOutRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkOut(checkOutRequest));
    }
}
