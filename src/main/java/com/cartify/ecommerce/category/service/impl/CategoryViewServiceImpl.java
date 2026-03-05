package com.cartify.ecommerce.category.service.impl;

import com.cartify.ecommerce.category.constants.CategoryConstants;
import com.cartify.ecommerce.category.mapper.CategoryMapper;
import com.cartify.ecommerce.category.model.CategoryStatus;
import com.cartify.ecommerce.category.repository.CategoryFullViewRepository;
import com.cartify.ecommerce.category.response.CategoryFullViewResponse;
import com.cartify.ecommerce.category.service.CategoryViewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoryViewServiceImpl implements CategoryViewService {

    private final CategoryFullViewRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryFullViewResponse> getAllCategories(Pageable pageable) {
        return this.repository
                .findAllByStatus(CategoryStatus.ACTIVE, pageable)
                .map(this.mapper::toFullViewResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryFullViewResponse getCategoryBySlug(String slug) {
        return this.repository
                .findBySlug(slug)
                .map(this.mapper::toFullViewResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryConstants.SLUG_NOT_FOUND, slug)
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryFullViewResponse> getCategoriesByParentId(Long parentId, Pageable pageable) {
        return this.repository
                .findAllByParentIdAndStatus(parentId, CategoryStatus.ACTIVE, pageable)
                .map(this.mapper::toFullViewResponse);
    }
}
