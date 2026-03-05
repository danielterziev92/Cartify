package com.cartify.ecommerce.category.service;

import com.cartify.ecommerce.category.dto.CategoryMetaDTO;
import com.cartify.ecommerce.category.response.CategoryMetaResponse;

public interface CategoryMetaService {

    CategoryMetaResponse getMetaByCategoryId(Long categoryId);

    CategoryMetaResponse createMeta(Long categoryId, CategoryMetaDTO categoryMetaDTO);

    CategoryMetaResponse updateMeta(Long categoryId, CategoryMetaDTO categoryMetaDTO);

    void deleteMeta(Long categoryId);
}
