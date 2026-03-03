package com.cartify.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "option_values",
        indexes = {
                @Index(name = "option_value_name_idx", columnList = "name")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_option_value_type_value", columnNames = {"option_type_id", "value"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionValue {
    public static final String VALUE_NOT_BLANK_MESSAGE = "Value cannot be blank";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = VALUE_NOT_BLANK_MESSAGE)
    @Column(nullable = false)
    private String value;

    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private int displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_type_id", nullable = false)
    private OptionType optionType;

    @OneToMany(mappedBy = "optionValue", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<VariantOptionValue> variantOptionalValues = new HashSet<>();
}
