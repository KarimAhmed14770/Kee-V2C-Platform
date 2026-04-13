package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.vendor.ShopRegisterRequest;
import com.Kee.V2C.dto.vendor.ShopUpdateRequest;
import com.Kee.V2C.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShopMapper {
    void updateShopFromDto(ShopUpdateRequest shopRequest,
                           @MappingTarget Shop shop);
}
