package com.cartify.ecommerce.category.service;

import com.cartify.ecommerce.category.dto.CategoryDTO;
import com.cartify.ecommerce.category.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getSubcategories(Long parentId);

    List<CategoryResponse> searchCategories(String name);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CategoryDTO categoryDTO);

    CategoryResponse updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryResponse moveCategory(Long id, Long newParentId);

    void deleteCategory(Long id);
}
