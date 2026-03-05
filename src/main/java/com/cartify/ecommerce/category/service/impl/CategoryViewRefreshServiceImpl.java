package com.cartify.ecommerce.category.service.impl;

import com.cartify.ecommerce.category.service.CategoryViewRefreshService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CategoryViewRefreshServiceImpl implements CategoryViewRefreshService {

    private final EntityManager entityManager;

    @Override
    @Async
    @Transactional
    public void refreshCategoryViews() {
        entityManager.createNativeQuery(
                "REFRESH MATERIALIZED VIEW CONCURRENTLY category_full_view"
        ).executeUpdate();
    }
}
