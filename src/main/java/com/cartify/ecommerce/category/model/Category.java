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
                @Index(name = "category_name_idx", columnList = "name", unique = true),
                @Index(name = "category_parent_idx", columnList = "parent_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Category> children = new HashSet<>();
}
