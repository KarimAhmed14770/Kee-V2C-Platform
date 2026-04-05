package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.dto.AuthenticationResponse;
import com.Kee.Ecommerce.dto.LoginRequest;
import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(UserRegistrationDTO userRegistrationDTO);
    AuthenticationResponse logIn(LoginRequest loginRequest);
}
