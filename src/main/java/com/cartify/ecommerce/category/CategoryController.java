package com.cartify.ecommerce.category;

import com.cartify.ecommerce.payload.PageMetaResponse;
import com.cartify.ecommerce.payload.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<CategoryResponse> page = this.service.getAllCategories(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryResponse response = this.service.createCategory(categoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryResponse response = this.service.updateCategory(id, categoryDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        this.service.getCategoryById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(CategoryConstants.CATEGORY_WAS_DELETED);
    }
}
