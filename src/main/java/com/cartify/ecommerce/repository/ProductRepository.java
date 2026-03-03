package com.cartify.ecommerce.repository;

import com.cartify.ecommerce.model.Product;
import com.cartify.ecommerce.model.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findAllByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findAllByCategoryIdAndStatus(Long categoryId, ProductStatus status, Pageable pageable);

    boolean existsByName(String name);
}
