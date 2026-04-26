package com.cartify.ecommerce.authorization.userrole.domain;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import com.cartify.ecommerce.authorization.role.domain.Role;
import com.cartify.ecommerce.shared.domain.exception.BusinessRuleException;
import com.cartify.ecommerce.shared.domain.vo.UserId;
import org.jmolecules.ddd.annotation.Service;
import org.jspecify.annotations.NonNull;

import java.util.Set;


/**
 * Domain service responsible for managing user role assignments and validating
 * permission hierarchies to ensure assigners have appropriate rights.
 */
@Service
public class UserRoleDomainService {

    /**
     * Assigns a role to a user after validating that the assigner has sufficient permissions.
     *
     * @param assignedPermissions the set of permissions that the assigner currently holds
     * @param targetUserId        the ID of the user to whom the role will be assigned
     * @param roleToAssign        the role to be assigned to the user
     * @param assignedById        the ID of the user performing the assignment
     * @return a new UserRole entity representing the assigned role
     * @throws BusinessRuleException if the assigner does not have sufficient permissions
     */
    public @NonNull UserRole assign(
            @NonNull Set<Permission> assignedPermissions,
            @NonNull UserId targetUserId,
            @NonNull Role roleToAssign,
            @NonNull UserId assignedById
    ) {
        Set<Permission> rolePermissions = roleToAssign.getPermissions();

        boolean exceedAssignerRights = rolePermissions.stream()
                .anyMatch(rolePermission -> assignedPermissions.stream()
                        .noneMatch(assignedPermission -> assignedPermission.equals(rolePermission)));

        if (exceedAssignerRights)
            throw new BusinessRuleException(UserRoleRule.EXCEEDS_ASSIGNER_RIGHTS_MSG);

        return UserRole.assign(targetUserId, roleToAssign.getId(), assignedById);
    }

    /**
     * Checks if a permission covers another permission, meaning the assigner's permission
     * includes all the rights of the role's permission.
     *
     * @param assigner the permission held by the assigner
     * @param role     the permission required by the role being assigned
     * @return true if assigner's permission covers the role's permission, false otherwise
     */
    private boolean covers(@NonNull Permission assigner, @NonNull Permission role) {
        if (!assigner.module().equals(role.module())) return false;

        if (assigner.resource() != null && !assigner.resource().equals(role.resource())) return false;

        return (assigner.mask() & role.mask()) == role.mask();
    }
}
