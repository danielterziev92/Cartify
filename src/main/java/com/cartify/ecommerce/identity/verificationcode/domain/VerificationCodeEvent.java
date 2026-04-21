package com.cartify.ecommerce.identity.verificationcode.domain;

import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Domain events published by the {@code VerificationCode} aggregate.
 *
 * <p>Every event carries the aggregate's {@link VerificationCodeId}, the owning
 * {@link UserId}, and the {@code occurredAt} timestamp at which it was raised.
 */
public sealed interface VerificationCodeEvent extends DomainEvent permits
        VerificationCodeEvent.Created,
        VerificationCodeEvent.Verified {

    @NonNull VerificationCodeId id();

    @NonNull UserId userId();

    @NonNull Instant occurredAt();

    /**
     * Raised when a new verification code is issued for a user.
     *
     * <p>The convenience constructor sets {@code occurredAt} to {@link Instant#now()}.
     *
     * @param id         identifier of the newly created verification code
     * @param userId     owner of the verification code
     * @param code       the plaintext code value sent to the user
     * @param expireAt   point in time after which the code is no longer valid
     * @param occurredAt when the event occurred
     */
    record Created(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant expireAt,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Created(
                @NonNull VerificationCodeId id,
                @NonNull UserId userId,
                @NonNull String code,
                @NonNull Instant expireAt) {
            this(id, userId, code, expireAt, Instant.now());
        }
    }

    /**
     * Raised when a user successfully submits the correct verification code.
     *
     * <p>The convenience constructor sets {@code occurredAt} to {@link Instant#now()}.
     *
     * @param id         identifier of the verified verification code
     * @param userId     owner of the verification code
     * @param occurredAt when the event occurred
     */
    record Verified(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull Instant occurredAt
    ) implements VerificationCodeEvent {
        public Verified(@NonNull VerificationCodeId id, @NonNull UserId userId) {
            this(id, userId, Instant.now());
        }
    }
}
