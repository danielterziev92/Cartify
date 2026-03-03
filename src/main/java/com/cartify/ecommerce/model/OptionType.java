package com.cartify.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "option_types",
        indexes = {
                @Index(name = "option_type_product_idx", columnList = "product_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionType {
    public static final String NAME_NOT_BLANK_MESSAGE = "Name cannot be blank";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = NAME_NOT_BLANK_MESSAGE)
    @Column(nullable = false)
    private String name;

    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private int displayOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "optionType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OptionValue> optionValues = new HashSet<>();
}
