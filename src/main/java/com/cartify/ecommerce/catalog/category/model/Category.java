package com.cartify.ecommerce.catalog.category.model;

import com.cartify.ecommerce.catalog.category.constants.CategoryConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_category_name", columnList = "name"),
                @Index(name = "idx_category_status", columnList = "status"),
                @Index(name = "idx_category_display_order", columnList = "display_order"),
                @Index(name = "idx_category_parent_id", columnList = "parent_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_category_slug", columnNames = "slug")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(length = CategoryConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(length = CategoryConstants.SLUG_MAX_LENGTH, nullable = false)
    private String slug;

    @Column(name = "image_url", length = CategoryConstants.URL_MAX_LENGTH)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> children;
}
