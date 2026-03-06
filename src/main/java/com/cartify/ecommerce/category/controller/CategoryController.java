package com.cartify.ecommerce.category.controller;

import com.cartify.ecommerce.category.dto.CategoryDTO;
import com.cartify.ecommerce.category.response.CategoryFullViewResponse;
import com.cartify.ecommerce.category.response.CategoryResponse;
import com.cartify.ecommerce.category.service.CategoryService;
import com.cartify.ecommerce.category.service.CategoryViewService;
import com.cartify.ecommerce.payload.PageMetaResponse;
import com.cartify.ecommerce.payload.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryViewService categoryViewService;

    @GetMapping
    public ResponseEntity<PageResponse<CategoryFullViewResponse>> getAllCategories(
            @RequestParam(required = false) Long parentId,
            @PageableDefault(sort = {"displayOrder", "id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<CategoryFullViewResponse> page = this.categoryViewService.getAllCategories(parentId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @GetMapping("slug/{slug}")
    public ResponseEntity<CategoryFullViewResponse> getCategoryBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.categoryViewService.getCategoryBySlug(slug));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getCategoryById(id));
    }

    @GetMapping("/admin/{id}/subcategories")
    public ResponseEntity<List<CategoryResponse>> getSubcategories(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getSubcategories(id));
    }

    @GetMapping("/admin/search")
    public ResponseEntity<List<CategoryResponse>> searchCategories(
            @RequestParam String name
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.searchCategories(name));
    }

    @PostMapping("/admin")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.createCategory(categoryDTO));
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.updateCategory(id, categoryDTO));
    }

    @PatchMapping("/admin/{id}/move")
    public ResponseEntity<CategoryResponse> moveCategory(
            @PathVariable Long id,
            @RequestParam(required = false) Long newParentId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.moveCategory(id, newParentId));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    ) {
        this.service.deleteCategory(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .<Void>build();
    }
}
