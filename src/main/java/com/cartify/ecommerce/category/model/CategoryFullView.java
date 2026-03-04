package com.cartify.ecommerce.category.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "category_full_view")
@Immutable
@Getter
public class CategoryFullView {

    @Id
    private Long id;

    private String name;
    private String slug;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "parent_id")
    private Long parentId;

    private String description;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;
}
