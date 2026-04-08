package com.Kee.V2C.dto;


import java.time.LocalDateTime;

public record CustomerRegistrationResponse(Long id,
                                           String firstName,
                                           String lastName,
                                           String email,
                                           String phoneNumber,
                                           LocalDateTime createdAt,
                                           String role) {}
