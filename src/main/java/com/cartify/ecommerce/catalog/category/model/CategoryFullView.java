package com.cartify.ecommerce.catalog.category.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("category_full_view")
@Getter
public class CategoryFullView {

    @Id
    private Long id;

    private String name;

    private String slug;

    private String imageUrl;

    private CategoryStatus status;

    private int displayOrder;

    private String description;

    private String seoTitle;

    private String seoDescription;

    private Long parentId;

    private List<CategoryChild> children;
}