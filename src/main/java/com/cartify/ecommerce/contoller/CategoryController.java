package com.cartify.ecommerce.contoller;

import com.cartify.ecommerce.payload.CategoryDTO;
import com.cartify.ecommerce.payload.CategoryResponse;
import com.cartify.ecommerce.payload.PageMetaResponse;
import com.cartify.ecommerce.payload.PageResponse;
import com.cartify.ecommerce.service.CategoryService;
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

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
            @PageableDefault(page = 0, size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<CategoryResponse> page = this.categoryService.getAllCategories(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new PageResponse<>(PageMetaResponse.from(page), page.getContent()));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryResponse response = this.categoryService.createCategory(categoryDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryResponse response = this.categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(this.categoryService.deleteCategory(id));
    }
}
