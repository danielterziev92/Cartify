package com.cartify.ecommerce.identity.verificationcode.domain;

import com.cartify.ecommerce.shared.domain.exception.BusinessRuleException;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing a one-time verification code issued to a user.
 *
 * <p>Create a new code via {@link #create(UserId)}. Reconstruct a persisted code via
 * {@link #reconstitute(VerificationCodeId, UserId, String, Instant)}.
 * Call {@link #verify(String)} to validate a submitted code against this aggregate,
 * then drain side effects with {@link #pullEvents()}.
 */
@Getter
public class VerificationCode implements AggregateRoot<VerificationCode, VerificationCodeId> {

    @Getter(AccessLevel.NONE)
    private static final SecureRandom numberGenerator = new SecureRandom();

    private final VerificationCodeId id;
    private final UserId userId;
    private final String code;
    private final Instant expireAt;

    @Getter(AccessLevel.NONE)
    private final List<VerificationCodeEvent> events;

    private VerificationCode(
            VerificationCodeId id,
            UserId userId,
            String code,
            Instant expireAt
    ) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.expireAt = expireAt;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new verification code for the given user and raises a {@link VerificationCodeEvent.Created} event.
     *
     * @param userId the user the code is issued to
     * @return a new {@code VerificationCode} with a generated code and calculated expiry
     */
    public static @NonNull VerificationCode create(@NonNull UserId userId) {
        VerificationCode verificationCode = new VerificationCode(
                VerificationCodeId.generate(),
                userId,
                generateCode(),
                Instant.now().plus(VerificationCodeRule.Expiry.EXPIRE_AFTER)
        );
        verificationCode.events.add(new VerificationCodeEvent.Created(
                verificationCode.id,
                userId,
                verificationCode.code,
                verificationCode.expireAt
        ));

        return verificationCode;
    }

    /**
     * Reconstitutes a {@code VerificationCode} from persisted state without raising any events.
     *
     * @param id       the persisted aggregate identifier
     * @param userId   the owner of the code
     * @param code     the stored code value
     * @param expireAt the stored expiry timestamp
     * @return a {@code VerificationCode} reflecting the stored state
     */
    public static @NonNull VerificationCode reconstitute(
            @NonNull VerificationCodeId id,
            @NonNull UserId userId,
            @NonNull String code,
            @NonNull Instant expireAt
    ) {
        return new VerificationCode(id, userId, code, expireAt);
    }

    /**
     * Validates the submitted code against this aggregate.
     *
     * <p>Raises a {@link VerificationCodeEvent.Verified} event on success.
     *
     * @param code the code submitted by the user
     * @throws BusinessRuleException if the code has expired or does not match
     */
    public void verify(@NonNull String code) {
        if (Instant.now().isAfter(this.expireAt))
            throw new BusinessRuleException(VerificationCodeRule.Expiry.EXPIRED_MSG);

        if (!this.code.equals(code))
            throw new BusinessRuleException(VerificationCodeRule.Code.INVALID_MSG, this.code, this.userId);

        this.events.add(new VerificationCodeEvent.Verified(this.id, this.userId));
    }

    /**
     * Returns all pending domain events and clears the internal list.
     * Intended to be called once after each command to drain side effects for publishing.
     *
     * @return an immutable snapshot of the events raised since the last pull
     */
    public @NonNull List<VerificationCodeEvent> pullEvents() {
        List<VerificationCodeEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of pending domain events without clearing them.
     *
     * @return an immutable copy of the current event list
     */
    public @NonNull List<VerificationCodeEvent> getEvents() {
        return List.copyOf(this.events);
    }

    /**
     * Generates a zero-padded numeric code of length {@link VerificationCodeRule.Code#LENGTH}.
     *
     * @return a randomly generated code string
     */
    private static @NonNull String generateCode() {
        int bound = 1;
        for (int i = 0; i < VerificationCodeRule.Code.LENGTH; i++) bound *= 10;

        return String.format("%0" + VerificationCodeRule.Code.LENGTH + "d", numberGenerator.nextInt(bound));
    }
}
