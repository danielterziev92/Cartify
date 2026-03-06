package com.cartify.ecommerce.category.service.impl;

import com.cartify.ecommerce.category.constants.CategoryConstants;
import com.cartify.ecommerce.category.constants.CategoryMetaConstants;
import com.cartify.ecommerce.category.dto.CategoryMetaDTO;
import com.cartify.ecommerce.category.mapper.CategoryMetaMapper;
import com.cartify.ecommerce.category.model.Category;
import com.cartify.ecommerce.category.model.CategoryMeta;
import com.cartify.ecommerce.category.repository.CategoryMetaRepository;
import com.cartify.ecommerce.category.repository.CategoryRepository;
import com.cartify.ecommerce.category.response.CategoryMetaResponse;
import com.cartify.ecommerce.category.service.CategoryMetaService;
import com.cartify.ecommerce.category.service.CategoryViewRefreshService;
import com.cartify.ecommerce.utils.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CategoryMetaServiceImpl implements CategoryMetaService {

    private final CategoryMetaRepository repository;
    private final CategoryMetaMapper mapper;

    private final CategoryRepository categoryRepository;
    private final CategoryViewRefreshService viewRefreshService;

    @Override
    @Transactional(readOnly = true)
    public CategoryMetaResponse getMetaByCategoryId(@NonNull Long categoryId) {
        return this.repository
                .findByCategoryId(categoryId)
                .map(this.mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryMetaConstants.CATEGORY_META_NOT_FOUND, categoryId)
                ));
    }

    @Override
    @Transactional
    public CategoryMetaResponse createMeta(@NonNull Long categoryId, @NonNull CategoryMetaDTO categoryMetaDTO) {
        if (this.repository.existsByCategoryId(categoryId))
            throw new EntityAlreadyExistsException(
                    String.format(CategoryMetaConstants.CATEGORY_META_ALREADY_EXISTS, categoryId)
            );

        Category category = this.categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryConstants.CATEGORY_NOT_FOUND, categoryId)
                ));

        CategoryMeta categoryMeta = this.mapper.toEntity(categoryMetaDTO);
        categoryMeta.setCategory(category);

        return this.saveAndRefresh(categoryMeta);
    }

    @Override
    @Transactional
    public CategoryMetaResponse updateMeta(@NonNull Long categoryId, @NonNull CategoryMetaDTO categoryMetaDTO) {
        CategoryMeta categoryMeta = this.repository
                .findByCategoryId(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryMetaConstants.CATEGORY_META_NOT_FOUND, categoryId)
                ));

        boolean descriptionChanged = !Objects.equals(categoryMeta.getDescription(), categoryMetaDTO.description());
        boolean seoTitleChanged = !Objects.equals(categoryMeta.getSeoTitle(), categoryMetaDTO.seoTitle());
        boolean seoDescriptionChanged = !Objects.equals(categoryMeta.getSeoDescription(), categoryMetaDTO.seoDescription());

        if (!descriptionChanged && !seoTitleChanged && !seoDescriptionChanged)
            return this.mapper.toResponse(categoryMeta);

        if (descriptionChanged)
            categoryMeta.setDescription(categoryMetaDTO.description());

        if (seoTitleChanged)
            categoryMeta.setSeoTitle(categoryMetaDTO.seoTitle());

        if (seoDescriptionChanged)
            categoryMeta.setSeoDescription(categoryMetaDTO.seoDescription());

        return this.saveAndRefresh(categoryMeta);
    }

    @Override
    @Transactional
    public void deleteMeta(@NonNull Long categoryId) {
        CategoryMeta categoryMeta = this.repository
                .findByCategoryId(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryMetaConstants.CATEGORY_META_NOT_FOUND, categoryId)
                ));

        this.repository.delete(categoryMeta);
        this.viewRefreshService.refreshCategoryViews();
    }

    private CategoryMetaResponse saveAndRefresh(@NotNull CategoryMeta categoryMeta) {
        CategoryMetaResponse response = this.mapper.toResponse(this.repository.save(categoryMeta));
        this.viewRefreshService.refreshCategoryViews();

        return response;
    }
}
