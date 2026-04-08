package com.Kee.V2C.mapper;

import com.Kee.V2C.dto.CategoryUpdateRequest;
import com.Kee.V2C.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    void updateCategoryFromDto(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Category category);
}
