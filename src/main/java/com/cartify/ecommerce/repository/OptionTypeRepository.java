package com.cartify.ecommerce.repository;

import com.cartify.ecommerce.model.OptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OptionTypeRepository extends JpaRepository<OptionType, Long> {

    List<OptionType> findAllByProductId(UUID productId);

    boolean existsByNameAndProductId(String name, UUID productId);
}
