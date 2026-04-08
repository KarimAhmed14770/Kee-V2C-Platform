package com.Kee.Ecommerce.dto;


import java.time.LocalDateTime;

public record CustomerProfileResponse(Long id,
                                      String firstName,
                                      String lastName,
                                      String phoneNumber,
                                      String imageUrl,
                                      String shippingAddress) { }
