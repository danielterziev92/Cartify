package com.cartify.ecommerce.identity.passwordreset.domain;

import com.cartify.ecommerce.shared.domain.exception.BusinessRuleException;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.jmolecules.ddd.types.AggregateRoot;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Aggregate root representing a password reset request.
 *
 * <p>A {@code PasswordReset} is created when a user initiates a reset flow. It holds a
 * securely generated, time-limited token that must be validated before the password can
 * be changed. Failed validation attempts are tracked and capped to prevent brute-force
 * attacks (see {@link PasswordResetRule.Attempt}).
 *
 * <p>Domain events ({@link PasswordResetEvent}) are accumulated internally and must be
 * drained via {@link #pullEvents()} after each operation.
 */
@Getter
public class PasswordReset implements AggregateRoot<PasswordReset, PasswordResetId> {

    @Getter(AccessLevel.NONE)
    private static final SecureRandom numberGenerator = new SecureRandom();

    private final PasswordResetId id;
    private final UserId userId;
    /**
     * URL-safe Base64 token without padding.
     */
    private final String token;
    private final Instant expiration;
    /**
     * Number of failed validation attempts so far.
     */
    private int attempts;

    @Getter(AccessLevel.NONE)
    private final List<PasswordResetEvent> events;


    private PasswordReset(
            @NonNull PasswordResetId id,
            @NonNull UserId userId,
            @NonNull String token,
            @NonNull Instant expiration,
            int attempts
    ) {
        this.id = id;
        this.userId = userId;
        this.token = token;
        this.expiration = expiration;
        this.attempts = attempts;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new password reset for the given user, generating a fresh token and
     * registering a {@link PasswordResetEvent.Created} event.
     */
    public static @NonNull PasswordReset create(@NonNull UserId userId) {
        PasswordReset passwordReset = new PasswordReset(
                PasswordResetId.generate(),
                userId,
                generateToken(),
                Instant.now().plus(PasswordResetRule.Expiration.EXPIRE_AFTER),
                0
        );
        passwordReset.events.add(new PasswordResetEvent.Created(passwordReset.id, passwordReset.userId));

        return passwordReset;
    }

    /**
     * Reconstitutes a {@code PasswordReset} from persistent state without raising events.
     * Intended for repository hydration only.
     */
    public static @NonNull PasswordReset reconstitute(
            @NonNull PasswordResetId id,
            @NonNull UserId userId,
            @NonNull String token,
            @NonNull Instant expiration,
            int attempts
    ) {
        return new PasswordReset(id, userId, token, expiration, attempts);
    }

    /**
     * Validates the supplied token against this reset request.
     *
     * <p>Enforces three rules in order: attempt limit not exceeded, token not expired,
     * token matches. Each failed match increments {@link #attempts} and registers a
     * {@link PasswordResetEvent.Attempted} event. A successful match registers
     * {@link PasswordResetEvent.Verified}.
     *
     * @throws BusinessRuleException if any rule is violated
     */
    public void validateToken(@NonNull String token) {
        if (this.attempts >= PasswordResetRule.Attempt.MAX_COUNT)
            throw new BusinessRuleException(PasswordResetRule.Attempt.EXCEEDED_MSG);

        if (Instant.now().isAfter(this.expiration))
            throw new BusinessRuleException(PasswordResetRule.Token.EXPIRED_MSG);

        if (!this.token.equals(token)) {
            this.attempts++;
            this.events.add(new PasswordResetEvent.Attempted(this.id));

            throw new BusinessRuleException(PasswordResetRule.Token.INVALID_MSG);
        }

        this.events.add(new PasswordResetEvent.Verified(this.id));
    }

    /**
     * Returns a snapshot of pending events and clears the internal list.
     * Call this once after each command to drain and dispatch events.
     */
    public @NonNull List<PasswordResetEvent> pullEvents() {
        List<PasswordResetEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a read-only view of pending events without clearing them.
     */
    public @NonNull List<PasswordResetEvent> getEvents() {
        return List.copyOf(this.events);
    }

    /**
     * Generates a cryptographically secure, URL-safe Base64 token without padding.
     */
    private static @NonNull String generateToken() {
        byte[] bytes = new byte[PasswordResetRule.Token.BYTE_LENGTH];
        numberGenerator.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
