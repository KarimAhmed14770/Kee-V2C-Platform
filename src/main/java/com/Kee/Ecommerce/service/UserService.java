package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.dto.*;
import com.Kee.Ecommerce.entity.User;

public interface UserService {
    UserResponseDTO register(UserRegistrationDTO userRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
    UserProfileResponse updateCustomerProfile(UserProfileRequest userProfileRequest);
    UserProfileResponse myProfile();
    UserProfileResponse partialUpdateCustomerProfile(Long id);
    CartResponse addToCart(CartItemRequest cartItemRequest);
    CartResponse viewMyCart();
}
