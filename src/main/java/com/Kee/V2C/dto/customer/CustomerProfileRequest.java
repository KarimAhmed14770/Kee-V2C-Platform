package com.Kee.V2C.dto.customer;

public record CustomerProfileRequest(String firstName,
                                 String lastName,
                                 String phoneNumber,
                                 String imageUrl,
                                 String address) { }
