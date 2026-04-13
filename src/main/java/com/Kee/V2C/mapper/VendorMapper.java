package com.Kee.V2C.mapper;


import com.Kee.V2C.dto.vendor.VendorUpdateProfileRequest;
import com.Kee.V2C.entity.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VendorMapper {
    void updateVendorFromDto(VendorUpdateProfileRequest shopRequest,
                             @MappingTarget Vendor vendor);
}
