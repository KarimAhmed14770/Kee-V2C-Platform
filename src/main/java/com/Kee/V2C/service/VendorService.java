package com.Kee.V2C.service;

import com.Kee.V2C.dto.*;

public interface VendorService {
    VendorProfileResponse updateVendorProfile(VendorProfileRequest vendorProfileRequest);
    VendorProfileResponse myProfile();

}
