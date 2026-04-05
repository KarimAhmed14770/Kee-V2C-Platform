package com.Kee.Ecommerce.dto;

public record UserProfileRequest(String firstName,
                                 String lastName,
                                 String phoneNumber,
                                 String imageUrl,
                                 String address) { }
