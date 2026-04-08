package com.Kee.Ecommerce.dto;


import java.time.LocalDateTime;
import java.util.List;

public record CustomerRegistrationResponse(Long id,
                                           String firstName,
                                           String lastName,
                                           String email,
                                           String phoneNumber,
                                           LocalDateTime createdAt,
                                           String role) {}
