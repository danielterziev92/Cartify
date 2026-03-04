package com.cartify.ecommerce.category.model;

import com.cartify.ecommerce.category.constants.CategoryConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "category_name_idx", columnList = "name"),
                @Index(name = "category_status_idx", columnList = "status"),
                @Index(name = "category_display_order_idx", columnList = "display_order, id"),
                @Index(name = "category_parent_status_idx", columnList = "parent_id, status"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_category_slug", columnNames = "slug")
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

    @Column(length = CategoryConstants.NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(length = CategoryConstants.SLUG_MAX_LENGTH, nullable = false)
    private String slug;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CategoryStatus status = CategoryStatus.DRAFT;

    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private int displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Category> children = new HashSet<>();

    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private CategoryMeta meta;
}
