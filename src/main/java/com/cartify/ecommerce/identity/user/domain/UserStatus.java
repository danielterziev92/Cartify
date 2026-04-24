package com.cartify.ecommerce.identity.user.domain;

import com.cartify.ecommerce.shared.domain.exception.InvalidValueException;
import org.jmolecules.ddd.types.ValueObject;

/**
 * Value object representing the lifecycle status of a user account.
 *
 * <p>Each constant enforces its own activation guard via {@link #requiredActive()},
 * throwing an {@link InvalidValueException} when the status does not permit active use.
 */
public enum UserStatus implements ValueObject {

    /**
     * The account is fully active and permitted to perform all operations.
     */
    ACTIVE {
        @Override
        public void requiredActive() {
        }
    },

    /**
     * The account is inactive and cannot be used until re-activated.
     */
    INACTIVE {
        @Override
        public void requiredActive() {
            throw new InvalidValueException(UserRule.Status.INACTIVE_MSG);
        }
    },

    /**
     * The account has been blocked by an administrator and cannot be used.
     */
    BLOCKED {
        @Override
        public void requiredActive() {
            throw new InvalidValueException(UserRule.Status.BLOCKED_MSG);
        }
    },

    /**
     * The account has been soft-deleted and is no longer accessible.
     */
    DELETED {
        @Override
        public void requiredActive() {
            throw new InvalidValueException(UserRule.Status.DELETED_MSG);
        }
    },
    /**
     * The account is awaiting email or identity verification and cannot be used yet.
     */
    PENDING_VERIFICATION {
        @Override
        public void requiredActive() {
            throw new InvalidValueException(UserRule.Status.PENDING_VERIFICATION_MSG);
        }
    };

    /**
     * Asserts that this status allows active use of the account.
     *
     * @throws InvalidValueException if the status is not {@link #ACTIVE}
     */
    public abstract void requiredActive();
}
