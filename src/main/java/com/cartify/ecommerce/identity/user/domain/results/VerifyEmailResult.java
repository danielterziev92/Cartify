package com.cartify.ecommerce.identity.user.domain.results;

import com.cartify.ecommerce.identity.user.domain.User;
import com.cartify.ecommerce.identity.user.domain.UserDomainService;
import com.cartify.ecommerce.identity.verificationcode.domain.VerificationCode;
import org.jspecify.annotations.NonNull;

/**
 * Result produced by {@link UserDomainService#verifyEmail}.
 *
 * <p>Carries the now-activated {@link User} and the consumed
 * {@link VerificationCode}. The caller is responsible for persisting with the
 * user and deleting the verification code.
 *
 * @param user             the user whose status has transitioned to {@code ACTIVE}
 * @param verificationCode the verification code that was consumed — must be deleted by the caller
 */
public record VerifyEmailResult(
        @NonNull User user,
        @NonNull VerificationCode verificationCode
) {
}
