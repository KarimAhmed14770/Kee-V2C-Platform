package com.Kee.Ecommerce.dto;

public record VendorRegistrationDto(String name,
                                    String description,
                                    String address,
                                    String imageUrl,
                                    String email,
                                    String userName,
                                    String password) {
}
