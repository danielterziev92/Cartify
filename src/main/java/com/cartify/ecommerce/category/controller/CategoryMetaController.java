package com.cartify.ecommerce.category.controller;

import com.cartify.ecommerce.category.dto.CategoryMetaDTO;
import com.cartify.ecommerce.category.response.CategoryMetaResponse;
import com.cartify.ecommerce.category.service.CategoryMetaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/{categoryId}/meta")
@AllArgsConstructor
public class CategoryMetaController {

    private final CategoryMetaService service;

    @GetMapping
    public ResponseEntity<CategoryMetaResponse> getMetaByCategoryId(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getMetaByCategoryId(categoryId));
    }

    @PostMapping
    public ResponseEntity<CategoryMetaResponse> createCategoryMeta(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryMetaDTO categoryMetaDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.service.createMeta(categoryId, categoryMetaDTO));
    }

    @PutMapping
    public ResponseEntity<CategoryMetaResponse> updateCategoryMeta(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryMetaDTO categoryMetaDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.updateMeta(categoryId, categoryMetaDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCategoryMeta(
            @PathVariable Long categoryId
    ) {
        this.service.deleteMeta(categoryId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .<Void>build();
    }
}
