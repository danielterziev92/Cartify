package com.cartify.ecommerce.identity.passwordreset.domain;

import com.cartify.ecommerce.shared.domain.vo.UserId;
import lombok.NonNull;
import org.jmolecules.event.types.DomainEvent;

import java.time.Instant;

/**
 * Sealed hierarchy of domain events emitted by the {@link PasswordReset} aggregate.
 *
 * <p>Every variant carries the {@link PasswordResetId} of the originating aggregate and
 * the {@link Instant} the event occurred. Timestamps are captured at construction time
 * via the convenience single-argument constructors.
 */
public sealed interface PasswordResetEvent extends DomainEvent permits
        PasswordResetEvent.Created,
        PasswordResetEvent.Attempted,
        PasswordResetEvent.Verified {

    /**
     * The identity of the {@link PasswordReset} that produced this event.
     */
    @NonNull
    PasswordResetId id();

    /**
     * Wall-clock time at which the event occurred.
     */
    @NonNull
    Instant occurredAt();

    /**
     * Raised when a new password reset is successfully created for a user.
     */
    record Created(@NonNull PasswordResetId id, @NonNull UserId userId, @NonNull Instant occurredAt)
            implements PasswordResetEvent {
        public Created(@NonNull PasswordResetId id, @NonNull UserId userId) {
            this(id, userId, Instant.now());
        }
    }

    /**
     * Raised on each failed token validation attempt.
     */
    record Attempted(@NonNull PasswordResetId id, @NonNull Instant occurredAt)
            implements PasswordResetEvent {
        public Attempted(@NonNull PasswordResetId id) {
            this(id, Instant.now());
        }
    }

    /**
     * Raised when the token is validated successfully, authorizing the password change.
     */
    record Verified(@NonNull PasswordResetId id, @NonNull Instant occurredAt)
            implements PasswordResetEvent {
        public Verified(@NonNull PasswordResetId id) {
            this(id, Instant.now());
        }
    }
}
