package com.cartify.ecommerce.repository;

import com.cartify.ecommerce.model.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionalValueRepository extends JpaRepository<OptionValue, Long> {

    List<OptionValue> findAllByOptionTypeId(Long optionTypeId);

    boolean existsByValueAndOptionTypeId(String value, Long optionTypeId);
}
