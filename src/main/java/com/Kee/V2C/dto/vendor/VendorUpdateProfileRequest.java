package com.Kee.V2C.dto.vendor;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VendorUpdateProfileRequest(
                                @Pattern(regexp = "^[a-zA-Z\\-']+$",message = "name can contain only letters, hyphens" +
                                    "or apostrophes")
                                @Size(min=2,max=50,message = "min is 2 characters, max is 50")
                                    String vendorName,

                                    String imageUrl,
                                @Size(min = 10, max = 200, message = "Address is too short. Please provide more details (Street, Building, etc.)")
                                @Pattern(
                                        regexp = "^[a-zA-Z0-9\\p{L}\\s\\.,'\\-\\/\\(\\)]+$",
                                        message = "Address contains invalid special characters"
                                )
                                    String address) {
}
