package com.cartify.ecommerce.identity.passwordreset.domain;

import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Repository for managing {@link PasswordReset} aggregates.
 *
 * <p>Provides persistence operations for password reset requests, including
 * lookup by token and active reset status per user.
 */
public interface PasswordResetRepository extends Repository<PasswordReset, PasswordResetId> {

    /**
     * Returns the password reset with the given ID if it exists.
     */
    Optional<PasswordReset> findById(@NonNull PasswordResetId id);

    /**
     * Returns the password reset associated with the given token, if it exists.
     */
    Optional<PasswordReset> findByToken(@NonNull String token);

    /**
     * Returns the active (non-expired, non-used) password reset for the given user if one exists.
     *
     * <p>At most one active reset is expected per user at any given time.
     */
    Optional<PasswordReset> findActiveByUserId(@NonNull UserId userId);

    /**
     * Persists the given password reset aggregate.
     */
    void save(@NonNull PasswordReset passwordReset);

    /**
     * Removes the password reset with the given ID.
     */
    void deleteById(@NonNull PasswordResetId id);
}
