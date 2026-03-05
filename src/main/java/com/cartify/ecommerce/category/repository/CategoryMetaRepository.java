package com.cartify.ecommerce.category.repository;

import com.cartify.ecommerce.category.model.CategoryMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryMetaRepository extends JpaRepository<CategoryMeta, Long> {

    Optional<CategoryMeta> findByCategoryId(Long categoryId);

    boolean existsByCategoryId(Long categoryId);
}
