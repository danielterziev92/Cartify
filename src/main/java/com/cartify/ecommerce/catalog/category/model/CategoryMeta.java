package com.cartify.ecommerce.catalog.category.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories_meta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryMeta {

    @Id
    private Long id;

    @Version
    private Long version;

    private String description;

    private String seoTitle;

    private String seoDescription;

    private Long categoryId;
}