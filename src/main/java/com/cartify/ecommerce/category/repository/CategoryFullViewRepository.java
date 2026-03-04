package com.cartify.ecommerce.category.repository;

import com.cartify.ecommerce.category.model.CategoryFullView;
import com.cartify.ecommerce.category.model.CategoryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryFullViewRepository extends JpaRepository<CategoryFullView, Long> {

    Page<CategoryFullView> findAllByStatus(CategoryStatus status, Pageable pageable);

    Page<CategoryFullView> findAllByParentIdAndStatus(Long parentId, CategoryStatus status, Pageable pageable);

    Optional<CategoryFullView> findBySlug(String slug);
}
