package com.Kee.V2C.dto;

import com.Kee.V2C.enums.UserStatus;

public record VendorProfileResponse(Long id,String name, String address, String image_url, float rating,
                                    String status) {
}
