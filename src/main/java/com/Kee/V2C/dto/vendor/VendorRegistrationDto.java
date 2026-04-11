package com.Kee.V2C.dto.vendor;

public record VendorRegistrationDto(String name,
                                    String description,
                                    String address,
                                    String imageUrl,
                                    String email,
                                    String userName,
                                    String password) {
}
