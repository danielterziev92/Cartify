package com.cartify.ecommerce.category.service;

import com.cartify.ecommerce.category.response.CategoryFullViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryViewService {

    Page<CategoryFullViewResponse> getAllCategories(Pageable pageable);

    CategoryFullViewResponse getCategoryBySlug(String slug);

    Page<CategoryFullViewResponse> getCategoriesByParentId(Long parentId, Pageable pageable);
}
