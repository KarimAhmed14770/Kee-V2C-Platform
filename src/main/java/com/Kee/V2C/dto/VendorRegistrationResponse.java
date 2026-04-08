package com.Kee.V2C.dto;

public record VendorRegistrationResponse(Long id,
                                         String name,
                                         String description,
                                         String address,
                                         String imageUrl,
                                         String email,
                                         String role) {
}
