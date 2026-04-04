package com.Kee.Ecommerce.dto;


import java.time.LocalDateTime;

public record CustomerProfileResponse(String firstName,
                                      String lastName,
                                      String phoneNumber,
                                      String imageUrl,
                                      String address,
                                      LocalDateTime updatedAt) { }
