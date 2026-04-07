package com.Kee.Ecommerce.dto;

public record CustomerProfileRequest(String firstName,
                                 String lastName,
                                 String phoneNumber,
                                 String imageUrl,
                                 String address) { }
