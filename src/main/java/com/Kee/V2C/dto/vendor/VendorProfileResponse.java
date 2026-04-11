package com.Kee.V2C.dto.vendor;

public record VendorProfileResponse(Long id,String name, String address, String image_url, float rating,
                                    String status) {
}
