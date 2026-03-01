package com.cartify.ecommerce.mapper;

import com.cartify.ecommerce.model.Category;
import com.cartify.ecommerce.payload.CategoryDTO;
import com.cartify.ecommerce.payload.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
    Category toEntity(CategoryDTO dto);
}
