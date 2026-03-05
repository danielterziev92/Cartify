package com.cartify.ecommerce.category.service.impl;

import com.cartify.ecommerce.category.constants.CategoryConstants;
import com.cartify.ecommerce.category.dto.CategoryDTO;
import com.cartify.ecommerce.category.mapper.CategoryMapper;
import com.cartify.ecommerce.category.model.Category;
import com.cartify.ecommerce.category.model.CategoryStatus;
import com.cartify.ecommerce.category.repository.CategoryRepository;
import com.cartify.ecommerce.category.response.CategoryResponse;
import com.cartify.ecommerce.category.service.CategoryService;
import com.cartify.ecommerce.category.service.CategoryViewRefreshService;
import com.cartify.ecommerce.utils.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;
    private CategoryMapper mapper;
    private final CategoryViewRefreshService viewRefreshService;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubcategories(@NonNull Long parentId) {
        this.findCategoryById(parentId);
        return this.repository
                .findAllByParentId(parentId)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> searchCategories(@NonNull String name) {
        return this.repository
                .findAllByNameContainingIgnoreCase(name)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(@NonNull Long id) {
        return this.repository
                .findById(id)
                .map(this.mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryConstants.CATEGORY_NOT_FOUND, id)
                ));
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(@NonNull CategoryDTO categoryDTO) {
        if (this.repository.existsBySlug(categoryDTO.slug()))
            throw new EntityAlreadyExistsException(
                    String.format(CategoryConstants.SLUG_ALREADY_EXISTS, categoryDTO.slug())
            );

        Category category = this.mapper.toEntity(categoryDTO);
        category.setStatus(categoryDTO.status() != null ? categoryDTO.status() : CategoryStatus.DRAFT);
        category.setDisplayOrder(categoryDTO.displayOrder() != null ? categoryDTO.displayOrder() : 0);

        if (categoryDTO.parentId() != null)
            category.setParent(this.findCategoryById(categoryDTO.parentId()));

        return this.saveAndRefresh(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(@NonNull Long id, @NonNull CategoryDTO categoryDTO) {
        Category category = this.findCategoryById(id);

        boolean nameChanged = !category.getName().equals(categoryDTO.name());
        boolean slugChanged = !category.getSlug().equals(categoryDTO.slug());
        Long currentParentId = category.getParent() != null ? category.getParent().getId() : null;
        boolean parentChanged = !Objects.equals(currentParentId, categoryDTO.parentId());
        boolean statusChanged = categoryDTO.status() != null && !category.getStatus().equals(categoryDTO.status());
        boolean displayOrderChanged = categoryDTO.displayOrder() != null && category.getDisplayOrder() != categoryDTO.displayOrder();
        boolean imageUrlChanged = !Objects.equals(category.getImageUrl(), categoryDTO.imageUrl());

        if (!nameChanged && !slugChanged && !parentChanged && !statusChanged && !displayOrderChanged && !imageUrlChanged)
            return this.mapper.toResponse(category);

        if (nameChanged)
            category.setName(categoryDTO.name());

        if (slugChanged) {
            if (this.repository.existsBySlug(categoryDTO.slug()))
                throw new EntityAlreadyExistsException(
                        String.format(CategoryConstants.SLUG_ALREADY_EXISTS, categoryDTO.slug())
                );

            category.setSlug(categoryDTO.slug());
        }

        if (parentChanged)
            category.setParent(categoryDTO.parentId() != null ? findCategoryById(categoryDTO.parentId()) : null);

        if (statusChanged)
            category.setStatus(categoryDTO.status());

        if (displayOrderChanged)
            category.setDisplayOrder(categoryDTO.displayOrder());

        if (imageUrlChanged)
            category.setImageUrl(categoryDTO.imageUrl());

        return this.saveAndRefresh(category);
    }

    @Override
    @Transactional
    public CategoryResponse moveCategory(@NonNull Long id, Long newParentId) {
        Category category = this.findCategoryById(id);
        Category newParent = null;

        if (newParentId != null) {
            if (newParentId.equals(id))
                throw new IllegalArgumentException(CategoryConstants.CATEGORY_OWN_PARENT);

            newParent = this.findCategoryById(newParentId);
        }

        category.setParent(newParent);

        return this.saveAndRefresh(category);
    }

    @Override
    @Transactional
    public void deleteCategory(@NonNull Long id) {
        this.repository.delete(this.findCategoryById(id));
        this.viewRefreshService.refreshCategoryViews();
    }

    private @NonNull Category findCategoryById(@NotNull Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryConstants.CATEGORY_NOT_FOUND, id)
                ));
    }

    private CategoryResponse saveAndRefresh(@NotNull Category category) {
        CategoryResponse response = this.mapper.toResponse(this.repository.save(category));
        this.viewRefreshService.refreshCategoryViews();

        return response;
    }
}
