package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.customer.CustomerUpdateProfileRequest;
import com.Kee.V2C.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {
    void updateCustomerFromDto(CustomerUpdateProfileRequest updateRequest, @MappingTarget Customer customer);
}
