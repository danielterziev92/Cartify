package com.cartify.ecommerce.identity.user.domain.results;

import com.cartify.ecommerce.identity.user.domain.User;
import com.cartify.ecommerce.identity.user.domain.UserDomainService;
import com.cartify.ecommerce.identity.verificationcode.domain.VerificationCode;
import org.jspecify.annotations.NonNull;

/**
 * Result produced by {@link UserDomainService#register}.
 *
 * <p>Carries both the newly created {@link User} aggregate and the
 * {@link VerificationCode} issued to it. The caller is responsible for
 * persisting both aggregates and sending the verification email.
 *
 * @param user             the newly created user — status is {@code PENDING_VERIFICATION}
 * @param verificationCode the one-time code issued to the user for email verification
 */
public record RegisterResult(
        @NonNull User user,
        @NonNull VerificationCode verificationCode
) {
}
