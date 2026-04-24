package com.cartify.ecommerce.identity.verificationcode.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * Constants governing verification code behavior, grouped by concern.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VerificationCodeRule {

    /**
     * Rules that apply to the code value itself.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {
        /**
         * Number of digits in a valid verification code.
         */
        public static final int LENGTH = 6;
        /**
         * Error message key returned when a code does not match.
         */
        public static final String INVALID_MSG = "verification-code.invalid";
    }

    /**
     * Rules that govern code expiry.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Expiry {
        /**
         * Number of hours before a verification code expires.
         */
        public static final int EXPIRE_TIME_HOURS = 1;
        /**
         * Duration after which a verification code is considered expired.
         */
        public static final Duration EXPIRE_AFTER = Duration.ofHours(EXPIRE_TIME_HOURS);
        /**
         * Error message key returned when a code has expired.
         */
        public static final String EXPIRED_MSG = "verification-code.expired";
    }
}
