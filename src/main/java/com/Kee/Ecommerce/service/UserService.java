package com.Kee.Ecommerce.service;


import com.Kee.Ecommerce.dto.UserRegistrationDTO;
import com.Kee.Ecommerce.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(UserRegistrationDTO userRegistrationDTO);
}
