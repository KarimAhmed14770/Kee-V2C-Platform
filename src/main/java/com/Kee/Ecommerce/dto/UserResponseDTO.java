package com.Kee.Ecommerce.dto;

import com.Kee.Ecommerce.entity.Role;
import com.Kee.Ecommerce.entity.User;
import com.Kee.Ecommerce.enums.UserRoles;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDTO( Long id,
         String firstName,
         String lastName,
         String email,
         String address,
         String phoneNumber,
         LocalDateTime createdAt,
         List<String> roles) {}
