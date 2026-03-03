package com.cartify.ecommerce.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByNameContainingIgnoreCase(String name);

    List<Category> findAllByParentId(Long parentId);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
