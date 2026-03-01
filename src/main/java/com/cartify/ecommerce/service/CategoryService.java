package com.cartify.ecommerce.service;

import com.cartify.ecommerce.payload.CategoryDTO;
import com.cartify.ecommerce.payload.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<CategoryResponse> getAllCategories(Pageable pageable);

    CategoryResponse getCategoryById(Long id);

    CategoryResponse createCategory(CategoryDTO categoryDTO);

    CategoryResponse updateCategory(Long id, CategoryDTO categoryDTO);

    String deleteCategory(Long id);
}
