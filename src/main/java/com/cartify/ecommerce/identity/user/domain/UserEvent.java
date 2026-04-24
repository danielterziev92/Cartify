package com.cartify.ecommerce.identity.user.domain;

import com.cartify.ecommerce.shared.domain.vo.Email;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.event.types.DomainEvent;
import org.jspecify.annotations.NonNull;

import java.time.Instant;

/**
 * Domain events published by the {@link User} aggregate.
 *
 * <p>Each event captures an immutable snapshot of a meaningful state change. The
 * {@code occurredAt} timestamp defaults to {@link Instant#now()} when using the
 * convenience constructors, allowing explicit timestamps to be supplied in tests.
 */
public sealed interface UserEvent extends DomainEvent permits
        UserEvent.Created,
        UserEvent.StatusChanged,
        UserEvent.PasswordChanged {

    /** Identity of the user this event belongs to. */
    @NonNull UserId id();

    /** Wall-clock time at which the event occurred. */
    @NonNull Instant occurredAt();

    /**
     * Raised when a new user account is registered.
     *
     * @param id          the newly assigned user identity
     * @param email       the email address used during registration
     * @param status      the initial account status
     * @param occurredAt  wall-clock time of registration
     */
    record Created(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull UserStatus status,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public Created(
                @NonNull UserId id,
                @NonNull Email email,
                @NonNull UserStatus status) {
            this(id, email, status, Instant.now());
        }
    }

    /**
     * Raised when a user's account status transitions (e.g. activation, suspension).
     *
     * @param id          the affected user identity
     * @param oldStatus   status before the transition
     * @param newStatus   status after the transition
     * @param occurredAt  wall-clock time of the transition
     */
    record StatusChanged(
            @NonNull UserId id,
            @NonNull UserStatus oldStatus,
            @NonNull UserStatus newStatus,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public StatusChanged(
                @NonNull UserId id,
                @NonNull UserStatus oldStatus,
                @NonNull UserStatus newStatus) {
            this(id, oldStatus, newStatus, Instant.now());
        }
    }

    /**
     * Raised when a user's password is changed or reset.
     *
     * @param id          the affected user identity
     * @param email       the user's email address, included for downstream notification use
     * @param occurredAt  wall-clock time of the password change
     */
    record PasswordChanged(
            @NonNull UserId id,
            @NonNull Email email,
            @NonNull Instant occurredAt
    ) implements UserEvent {
        public PasswordChanged(@NonNull UserId id, @NonNull Email email) {
            this(id, email, Instant.now());
        }
    }
}
