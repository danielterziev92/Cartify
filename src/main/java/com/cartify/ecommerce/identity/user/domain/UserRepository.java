package com.cartify.ecommerce.identity.user.domain;

import com.cartify.ecommerce.shared.domain.vo.Email;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Repository for managing {@link User} aggregates.
 *
 * <p>Provides persistence operations for user accounts, including
 * lookup by ID and email address.
 */
public interface UserRepository extends Repository<User, UserId> {

    /**
     * Returns the user with the given ID if it exists.
     */
    Optional<User> findById(@NonNull UserId id);

    /**
     * Returns the user with the given email address if one exists.
     */
    Optional<User> findByEmail(@NonNull Email email);

    /**
     * Returns {@code true} if a user with the given email address already exists.
     */
    boolean existsByEmail(@NonNull Email email);

    /**
     * Persists the given user aggregate.
     */
    void save(@NonNull User user);
}
