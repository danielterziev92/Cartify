package com.cartify.ecommerce.category.event;

import org.springframework.context.ApplicationEvent;

public class CategoryViewRefreshEvent extends ApplicationEvent {
    public CategoryViewRefreshEvent(Object source) {
        super(source);
    }
}
