package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.entity.User;

public interface CustomerService {
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationDTO customerRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
    UserProfileResponse myProfile();
    UserProfileResponse partialUpdateCustomerProfile(UserProfileRequest updateRequest);
    CartResponse addToCart(CartItemRequest cartItemRequest);
    CartResponse viewMyCart();
    CheckoutResponse checkOut(CheckOutRequest checkOutRequest);
    InvoiceResponse generateInvoice(long orderId);
}
