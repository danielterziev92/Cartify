package com.cartify.ecommerce.catalog.category.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    private Long id;

    @Version
    private Long version;

    private String name;

    private String slug;

    private String imageUrl;

    private CategoryStatus status;

    private int displayOrder;

    private Long parentId;
}