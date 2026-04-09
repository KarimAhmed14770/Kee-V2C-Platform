package com.Kee.V2C.dto;


public record CustomerProfileResponse(Long id,
                                      String firstName,
                                      String lastName,
                                      String phoneNumber,
                                      String imageUrl,
                                      String shippingAddress,
                                      String status) { }
