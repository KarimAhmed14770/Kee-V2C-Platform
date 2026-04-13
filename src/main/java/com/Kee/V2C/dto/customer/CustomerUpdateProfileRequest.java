package com.Kee.V2C.dto.customer;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerUpdateProfileRequest(
                                 @Pattern(regexp = "^[a-zA-Z\\-']+$",message = "name can contain only letters, hyphens" +
                                         "or apostrophes")
                                 @Size(min=2,max=50,message = "min is 2 characters, max is 50")
                                 String firstName,

                                 @Pattern(regexp = "^[a-zA-Z\\-']+$",message = "name can contain only letters, hyphens" +
                                         "or apostrophes")
                                 @Size(min=2,max=50,message = "min is 2 characters, max is 50")
                                 String lastName,

                                 @Size(min=11,max = 11,message = "enter a valid egyptian mobile number")
                                 @Pattern(
                                         regexp = "^01[0125][0-9]{8}$",
                                         message = "Phone number must be a valid Egyptian mobile number (11 digits starting with 010, 011, 012, or 015)"
                                 )
                                 String phoneNumber,

                                 String imageUrl,

                                 @Size(min = 10, max = 200, message = "Address is too short. Please provide more details (Street, Building, etc.)")
                                 @Pattern(
                                         regexp = "^[a-zA-Z0-9\\p{L}\\s\\.,'\\-\\/\\(\\)]+$",
                                         message = "Address contains invalid special characters"
                                 )
                                 String address) { }
