package com.cartify.ecommerce.category.model;

import com.cartify.ecommerce.category.constants.CategoryMetaConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "categories_meta",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_category_meta_category_id", columnNames = "category_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "seo_title", length = CategoryMetaConstants.SEO_TITLE_MAX_LENGTH)
    private String seoTitle;

    @Column(name = "seo_description", length = CategoryMetaConstants.SEO_DESCRIPTION_MAX_LENGTH)
    private String seoDescription;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
