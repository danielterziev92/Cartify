package com.cartify.ecommerce.catalog.category.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Immutable
@Subselect("SELECT * FROM category_full_view")
@Synchronize({"categories", "categories_meta"})
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

    private String description;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "parent_id")
    private Long parentId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<CategoryChild> children;
}
