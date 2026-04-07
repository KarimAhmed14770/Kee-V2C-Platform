package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.entity.Product;
import com.Kee.Ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO register(UserRegistrationDTO userRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
    UserProfileResponse myProfile();
    UserProfileResponse partialUpdateCustomerProfile(UserProfileRequest updateRequest);
    CartResponse addToCart(CartItemRequest cartItemRequest);
    CartResponse viewMyCart();
    CheckoutResponse checkOut(CheckOutRequest checkOutRequest);
}
