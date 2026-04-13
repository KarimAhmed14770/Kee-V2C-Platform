package com.Kee.V2C.dto.vendor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VendorRegistrationDto(
                                @NotBlank(message = "Name can't be empty")
                                @Pattern(regexp = "^[a-zA-Z\\-']+$",message = "name can contain only letters, hyphens" +
                                                                                "or apostrophes")
                                @Size(min=2,max=50,message = "min is 2 characters, max is 50")
                                    String name,

                                @Size(max = 500, message = "Description cannot exceed 500 characters")
                                    String description,

                                @Size(min = 10, max = 200, message = "Address is too short. Please provide more details (Street, Building, etc.)")
                                @Pattern(
                                        regexp = "^[a-zA-Z0-9\\p{L}\\s\\.,'\\-\\/\\(\\)]+$",
                                        message = "Address contains invalid special characters"
                                )
                                    String address,


                                    String imageUrl,

                                @NotBlank(message = "email is required")
                                @Email(message = "Please provide a valid email address")
                                @Pattern(
                                        regexp = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                                        message = "Email format is invalid")
                                    String email,

                                @NotBlank(message = "field required")
                                @Size(min=5,message = "minimum is 5 characters")
                                String userName,
                                @NotBlank(message = "Password is required")
                                @Size(min = 8, message = "Password must be at least 8 characters long")
                                @Pattern(
                                regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                                message = "Password must contain at least one uppercase letter and one special character"
                                )
                                    String password) { }
