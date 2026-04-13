package com.Kee.V2C.dto.vendor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ShopRegisterRequest(
                            @NotBlank(message = "Shop name is required")
                            @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
                            @Pattern(regexp = "^[a-zA-Z\\s&]+$", message = "Name can only contain letters, spaces, and '&'")
                          String name,

                            @NotBlank(message = "Shop address is required")
                            @Size(min = 10, max = 200, message = "Address is too short. Please provide more details (Street, Building, etc.)")
                            @Pattern(
                                    regexp = "^[a-zA-Z0-9\\p{L}\\s\\.,'\\-\\/\\(\\)]+$",
                                    message = "Address contains invalid special characters"
                            )
                          String address,
                          Boolean active) {
}
