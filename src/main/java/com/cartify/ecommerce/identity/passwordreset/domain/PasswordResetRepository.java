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
     * Finds a password reset by its unique identity.
     *
     * @param id the password reset identity
     * @return the password reset or empty if not found
     */
    Optional<PasswordReset> findById(@NonNull PasswordResetId id);

    /**
     * Finds a password reset by its reset token.
     *
     * @param token the reset token value
     * @return the password reset, or empty if no match is found
     */
    Optional<PasswordReset> findByToken(@NonNull String token);

    /**
     * Finds the active password reset belonging to the given user.
     *
     * <p>At most one active reset is expected per user at any given time.
     *
     * @param userId the ID of the owning user
     * @return the password reset, or empty if the user has no active reset
     */
    Optional<PasswordReset> findByUserId(@NonNull UserId userId);

    /**
     * Persists a new or updated password reset aggregate.
     *
     * @param passwordReset the aggregate to save
     */
    void save(@NonNull PasswordReset passwordReset);

    /**
     * Deletes the password reset with the given identity.
     *
     * @param id the identity of the password reset to delete
     */
    void deleteById(@NonNull PasswordResetId id);
}
