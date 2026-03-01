package com.cartify.ecommerce.service;

import com.cartify.ecommerce.mapper.CategoryMapper;
import com.cartify.ecommerce.model.Category;
import com.cartify.ecommerce.payload.CategoryDTO;
import com.cartify.ecommerce.payload.CategoryResponse;
import com.cartify.ecommerce.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    public static String CATEGORY_NOT_FOUND = "Category with id: %d was not found";

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return this.categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return this.categoryRepository
                .findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND, id)));
    }

    @Override
    public CategoryResponse createCategory(CategoryDTO categoryDTO) {
        Category saved = this.categoryRepository.save(categoryMapper.toEntity(categoryDTO));
        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = this.categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND, id)));

        category.setName(categoryDTO.name());
        this.categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = this.categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND, id)));
        this.categoryRepository.delete(category);
        return String.format("Deleted category with id: %d", id);
    }
}
