package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.CustomerProfileRequest;
import com.Kee.V2C.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {
    void updateCustomerFromDto(CustomerProfileRequest updateRequest, @MappingTarget Customer customer);
}
