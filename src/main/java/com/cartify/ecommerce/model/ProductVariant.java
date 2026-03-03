package com.cartify.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "product_variants",
        indexes = {
                @Index(name = "product_variant_product_idx", columnList = "product_id"),
                @Index(name = "product_variant_sku_idx", columnList = "sku")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {
    public static final String ADDITIONAL_PRICE_NOT_NEGATIVE_MESSAGE = "Additional price cannot be negative";
    public static final String QUANTITY_NOT_NULL_MESSAGE = "Stock quantity cannot be negative";
    public static final String SKU_BETWEEN_CHARACTERS_MESSAGE = "SKU must be between {min} and {max} characters";

    public static final int ADDITIONAL_PRICE_PRECISION = 10;
    public static final int ADDITIONAL_PRICE_SCALE = 2;
    public static final int SKU_MIN_LENGTH = 1;
    public static final int SKU_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_seq")
    @SequenceGenerator(name = "product_variant_seq", sequenceName = "product_variant_sequence", allocationSize = 10)
    private Long id;

    @Size(min = SKU_MIN_LENGTH, max = SKU_MAX_LENGTH, message = SKU_BETWEEN_CHARACTERS_MESSAGE)
    @Column(unique = true)
    private String sku;

    @DecimalMin(value = "0.0", message = ADDITIONAL_PRICE_NOT_NEGATIVE_MESSAGE)
    @Column(name = "additional_price", nullable = false, precision = ADDITIONAL_PRICE_PRECISION, scale = ADDITIONAL_PRICE_SCALE)
    @Builder.Default
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @Min(value = 0, message = QUANTITY_NOT_NULL_MESSAGE)
    @Column(nullable = false)
    @Builder.Default
    private int quantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<VariantOptionValue> variantOptionValues = new HashSet<>();
}
