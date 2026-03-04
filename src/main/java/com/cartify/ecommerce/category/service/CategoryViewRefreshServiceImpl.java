package com.cartify.ecommerce.category.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
