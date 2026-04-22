package com.cartify.ecommerce.identity.verificationcode.domain;

import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.ddd.types.Repository;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

/**
 * Repository for managing {@link VerificationCode} aggregates.
 *
 * <p>Each user may have at most one active verification code at a time.
 * Codes are looked up by their identity, the owning user, or the raw OTP value.
 */
public interface VerificationCodeRepository extends Repository<VerificationCode, VerificationCodeId> {

    /**
     * Finds a verification code by its unique identity.
     *
     * @param id the verification code identity
     * @return the verification code, or empty if not found
     */
    Optional<VerificationCode> findById(@NonNull VerificationCodeId id);

    /**
     * Finds the active verification code belonging to the given user.
     *
     * @param userId the ID of the owning user
     * @return the verification code, or empty if the user has no active code
     */
    Optional<VerificationCode> findByUserId(@NonNull UserId userId);

    /**
     * Finds a verification code by its raw OTP value.
     *
     * @param code the plain-text OTP value
     * @return the verification code, or empty if no match is found
     */
    Optional<VerificationCode> findByCode(@NonNull String code);

    /**
     * Persists a new or updated verification code.
     *
     * @param verificationCode the aggregate to save
     */
    void save(@NonNull VerificationCode verificationCode);

    /**
     * Deletes the verification code with the given identity.
     *
     * @param id the identity of the verification code to delete
     */
    void deleteById(@NonNull VerificationCodeId id);
}
