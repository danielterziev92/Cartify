package com.cartify.ecommerce.category.mapper;

import com.cartify.ecommerce.category.model.Category;
import com.cartify.ecommerce.category.response.CategoryResponse;
import com.cartify.ecommerce.category.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDTO dto);
}
