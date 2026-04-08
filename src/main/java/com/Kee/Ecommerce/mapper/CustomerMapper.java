package com.Kee.Ecommerce.mapper;

import com.Kee.Ecommerce.dto.CustomerProfileRequest;
import com.Kee.Ecommerce.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {
    void updateProductFromDto(CustomerProfileRequest updateRequest, @MappingTarget Customer customer);
}
