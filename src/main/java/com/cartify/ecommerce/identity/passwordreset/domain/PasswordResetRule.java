package com.cartify.ecommerce.identity.passwordreset.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Namespace for password-reset business rules and i18n message keys.
 *
 * <p>Constants are grouped into nested classes by concern: {@link Expiration},
 * {@link Token}, and {@link Attempt}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordResetRule {

    /**
     * Token lifetime policy.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Expiration {
        /**
         * Minutes a newly issued token remains valid.
         */
        public static final int MINUTES = 15;

        /**
         * Duration after which a verification code is considered expired.
         */
        public static final Duration EXPIRE_AFTER = Duration.ofMinutes(MINUTES);
    }

    /**
     * Token format and validation message keys.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Token {
        /**
         * Number of random bytes used for token generation (256-bit entropy).
         */
        public static final int BYTE_LENGTH = 32;

        /**
         * i18n key used when the supplied token does not match.
         */
        public static final String INVALID_MSG = "password-reset.token.invalid";
        /**
         * i18n key used when the token has passed its expiration time.
         */
        public static final String EXPIRED_MSG = "password-reset.token.expired";
    }

    /**
     * Failed-attempt limits and message keys.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Attempt {
        /**
         * Maximum number of failed validation attempts before the reset is locked.
         */
        public static final int MAX_COUNT = 3;
        /**
         * i18n key used when {@link #MAX_COUNT} has been reached or exceeded.
         */
        public static final String EXCEEDED_MSG = "password-reset.attempt.exceeded";
    }
}
