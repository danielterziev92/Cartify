package com.cartify.ecommerce.identity.user.domain;

import com.cartify.ecommerce.shared.domain.vo.Email;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import lombok.AccessLevel;
import lombok.Getter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code User} aggregate root, representing a registered account in the identity domain.
 *
 * <p>State changes are expressed through {@link UserEvent}s collected internally and drained
 * via {@link #pullEvents()} after each command. Reconstitution from persistence is handled
 * through the overloaded {@code reconstitute} factory methods; callers should only supply
 * the fields actually projected by their query.
 */
@Getter
public class User implements AggregateRoot<User, UserId> {

    private final UserId id;
    private UserStatus status;
    private final Email email;
    private PasswordHash passwordHash;

    @Getter(AccessLevel.NONE)
    private final List<UserEvent> events;

    private User(
            UserId id,
            UserStatus status,
            Email email,
            PasswordHash passwordHash
    ) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.passwordHash = passwordHash;
        this.events = new ArrayList<>(2);
    }

    /**
     * Creates a new user account, assigning a generated {@link UserId} and setting the initial
     * status to {@link UserStatus#PENDING_VERIFICATION}. Emits a {@link UserEvent.Created} event.
     *
     * @param email        raw email address string; validated on construction
     * @param passwordHash pre-hashed BCrypt password string; validated on construction
     */
    public static @NonNull User create(@NonNull String email, @NonNull String passwordHash) {
        Email emailVO = new Email(email);
        PasswordHash passwordHashVO = new PasswordHash(passwordHash);

        User user = new User(UserId.generate(), UserStatus.PENDING_VERIFICATION, emailVO, passwordHashVO);
        user.events.add(new UserEvent.Created(user.id, user.email, user.status));

        return user;
    }

    /**
     * Reconstitutes a {@code User} from persistence with only identity and status.
     * {@code email} and {@code passwordHash} will be {@code null}.
     */
    public static @NonNull User reconstitute(@NonNull UserId id, @NonNull UserStatus status) {
        return new User(id, status, null, null);
    }

    /**
     * Reconstitutes a {@code User} from persistence with identity, status, and email.
     * {@code passwordHash} will be {@code null}.
     */
    public static @NonNull User reconstitute(@NonNull UserId id, @NonNull UserStatus status, @NonNull Email email) {
        return new User(id, status, email, null);
    }

    /**
     * Reconstitutes a {@code User} from persistence with identity, status, and password hash.
     * {@code email} will be {@code null}.
     */
    public static @NonNull User reconstitute(
            @NonNull UserId id,
            @NonNull UserStatus status,
            @NonNull PasswordHash passwordHash
    ) {
        return new User(id, status, null, passwordHash);
    }

    /**
     * Reconstitutes a fully-hydrated {@code User} from persistence with all fields.
     * Either {@code email} or {@code passwordHash} may be {@code null} when not projected.
     */
    public static @NonNull User reconstitute(
            @NonNull UserId id,
            @NonNull UserStatus status,
            @Nullable Email email,
            @Nullable PasswordHash passwordHash
    ) {
        return new User(id, status, email, passwordHash);
    }

    /**
     * Transitions the user to {@link UserStatus#ACTIVE}. Emits a {@link UserEvent.StatusChanged} event.
     */
    public void activate() {
        this.changeStatus(UserStatus.ACTIVE);
    }

    /**
     * Transitions the user to {@link UserStatus#INACTIVE}. Emits a {@link UserEvent.StatusChanged} event.
     */
    public void deactivate() {
        this.changeStatus(UserStatus.INACTIVE);
    }

    /**
     * Transitions the user to {@link UserStatus#BLOCKED}. Emits a {@link UserEvent.StatusChanged} event.
     */
    public void block() {
        this.changeStatus(UserStatus.BLOCKED);
    }

    /**
     * Soft-deletes the user by transitioning to {@link UserStatus#DELETED}. Emits a {@link UserEvent.StatusChanged} event.
     */
    public void softDelete() {
        this.changeStatus(UserStatus.DELETED);
    }

    /**
     * Transitions the user to the given status. No-ops when the status is unchanged.
     * Emits a {@link UserEvent.StatusChanged} event on a real transition.
     *
     * @param newStatus the target status
     */
    public void changeStatus(@NonNull UserStatus newStatus) {
        if (this.status.equals(newStatus)) return;

        UserStatus oldStatus = this.status;
        this.status = newStatus;
        this.events.add(new UserEvent.StatusChanged(this.id, oldStatus, this.status));
    }

    /**
     * Replaces the current password hash. Requires the account to be {@link UserStatus#ACTIVE}
     * and both {@code passwordHash} and {@code email} fields to be loaded. No-ops when the new
     * hash equals the current one. Emits a {@link UserEvent.PasswordChanged} event on change.
     *
     * @param newPasswordHash the validated BCrypt hash to set
     * @throws IllegalStateException if the account is not active or fields are not loaded
     */
    public void changePassword(@NonNull PasswordHash newPasswordHash) {
        this.status.requiredActive();
        Objects.requireNonNull(this.passwordHash);
        Objects.requireNonNull(this.email);

        if (this.passwordHash.equals(newPasswordHash)) return;

        this.passwordHash = newPasswordHash;
        this.events.add(new UserEvent.PasswordChanged(this.id, this.email));
    }

    /**
     * Drains and returns all pending domain events, clearing the internal buffer.
     * Call this once per command after persistence to dispatch events downstream.
     */
    public @NonNull List<UserEvent> pullEvents() {
        List<UserEvent> snapshot = List.copyOf(this.events);
        this.events.clear();
        return snapshot;
    }

    /**
     * Returns a snapshot of pending events without clearing the buffer. Intended for testing.
     */
    public @NonNull List<UserEvent> getEvents() {
        return List.copyOf(this.events);
    }
}
