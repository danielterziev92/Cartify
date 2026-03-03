package com.cartify.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "product_name_idx", columnList = "name"),
                @Index(name = "product_status_idx", columnList = "status"),
                @Index(name = "product_category_idx", columnList = "category_id"),
                @Index(name = "product_category_status_idx", columnList = "category_id, status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    public static final String NAME_NOT_BLANK_MESSAGE = "Name cannot be blank";
    public static final String BASE_PRICE_NOT_NULL_MESSAGE = "Base price cannot be null";
    public static final String BASE_PRICE_NOT_NEGATIVE_MESSAGE = "Base price cannot be negative";

    public static final int BASE_PRICE_PRECISION = 10;
    public static final int BASE_PRICE_SCALE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = BASE_PRICE_NOT_NULL_MESSAGE)
    @DecimalMin(value = "0.0", inclusive = false, message = BASE_PRICE_NOT_NEGATIVE_MESSAGE)
    @Column(name = "base_price", nullable = false, precision = BASE_PRICE_PRECISION, scale = BASE_PRICE_SCALE)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProductStatus status = ProductStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OptionType> optionTypes = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProductVariant> variants = new HashSet<>();
}
