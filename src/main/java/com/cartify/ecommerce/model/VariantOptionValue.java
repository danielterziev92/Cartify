package com.cartify.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "variant_option_values",
        indexes = {
                @Index(name = "variant_option_value_variant_idx", columnList = "variant_id"),
                @Index(name = "variant_option_value_option_value_idx", columnList = "option_value_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_variant_option_value", columnNames = {"variant_id", "option_value_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantOptionValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "variant_option_value_seq")
    @SequenceGenerator(name = "variant_option_value_seq", sequenceName = "variant_option_value_sequence", allocationSize = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id", nullable = false)
    private OptionValue optionValue;
}
