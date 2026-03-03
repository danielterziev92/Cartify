package com.cartify.ecommerce.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = {"children"})
    Page<Category> findAllByParentIsNull(Pageable pageable);

    @EntityGraph(attributePaths = {"children"})
    List<Category> findAllByParentId(Long parentId);

    @EntityGraph(attributePaths = {"children"})
    Optional<Category> findWithChildrenById(Long id);

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
