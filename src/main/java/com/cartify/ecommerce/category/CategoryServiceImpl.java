package com.cartify.ecommerce.category;

import com.cartify.ecommerce.exception.EntityAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository repository;
    private CategoryMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return this.repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategoriesWithChildren(Pageable pageable) {
        return this.repository.findAllByParentIsNull(pageable)
                .map(this.mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubcategories(Long parentId) {
        getCategoryById(parentId);

        return this.repository.findAllByParentId(parentId)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> searchCategories(String name) {
        return this.repository.findAllByNameContainingIgnoreCase(name)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        return repository.findWithChildrenById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(CategoryConstants.CATEGORY_NOT_FOUND));
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryDTO categoryDTO) {
        if (this.repository.existsByName(categoryDTO.name()))
            throw new EntityNotFoundException(CategoryConstants.CATEGORY_ALREADY_EXISTS);

        Category category = this.mapper.toEntity(categoryDTO);

        if (categoryDTO.parentId() != null) {
            Category parent = this.repository.findById(categoryDTO.parentId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format(CategoryConstants.CATEGORY_NOT_FOUND, categoryDTO.parentId())
                    ));

            category.setParent(parent);
        }

        return this.mapper.toResponse(this.repository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = this.findCategoryById(id);

        boolean nameChanged = !category.getName().equals(categoryDTO.name());
        Long currentParentId = category.getParent() != null ? category.getParent().getId() : null;
        boolean parentChanged = !Objects.equals(currentParentId, categoryDTO.parentId());

        if (!nameChanged && !parentChanged)
            return this.mapper.toResponse(category);

        if (nameChanged) {
            if (this.repository.existsByName(categoryDTO.name()))
                throw new EntityAlreadyExistsException(String.format(CategoryConstants.CATEGORY_ALREADY_EXISTS, categoryDTO.name()));

            category.setName(categoryDTO.name());
        }

        if (parentChanged) {
            category.setParent(categoryDTO.parentId() != null ? this.findCategoryById(categoryDTO.parentId()) : null);
        }

        return this.mapper.toResponse(this.repository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse moveCategory(Long id, Long newParentId) {
        Category category = this.findCategoryById(id);

        Category newParent = null;

        if (newParentId != null) {
            if (newParentId.equals(id))
                throw new IllegalArgumentException(CategoryConstants.CATEGORY_OWN_PARENT);

            newParent = this.findCategoryById(newParentId);
        }

        category.setParent(newParent);

        return this.mapper.toResponse(this.repository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = this.findCategoryById(id);
        this.repository.delete(category);
    }

    private @NonNull Category findCategoryById(@NotNull Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(CategoryConstants.CATEGORY_NOT_FOUND, id)
                ));
    }
}
