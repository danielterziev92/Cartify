package com.cartify.ecommerce.repository;

import com.cartify.ecommerce.model.VariantOptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantOptionValueRepository extends JpaRepository<VariantOptionValue, Long> {

    List<VariantOptionValue> findAllByProductVariantId(Long variantId);

    boolean existsByProductVariantIdAndOptionValueId(Long variantId, Long optionValueId);
}
