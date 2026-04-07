package com.Kee.Ecommerce.mapper;

import com.Kee.Ecommerce.dto.ProductRequest;
import com.Kee.Ecommerce.dto.UserProfileRequest;
import com.Kee.Ecommerce.entity.Product;
import com.Kee.Ecommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    void updateProductFromDto(UserProfileRequest updateRequest, @MappingTarget User user);
}
