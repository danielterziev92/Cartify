package com.cartify.ecommerce.category.service.impl;

import com.cartify.ecommerce.category.event.CategoryViewRefreshEvent;
import com.cartify.ecommerce.category.service.CategoryViewRefreshService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@AllArgsConstructor
public class CategoryViewRefreshServiceImpl implements CategoryViewRefreshService {

    private final EntityManager entityManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void refreshCategoryViews() {
        this.applicationEventPublisher.publishEvent(new CategoryViewRefreshEvent(this));
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onRefreshEvent(CategoryViewRefreshEvent event) {
        this.entityManager.createNativeQuery(
                "REFRESH MATERIALIZED VIEW CONCURRENTLY category_full_view"
        ).executeUpdate();
    }
}
