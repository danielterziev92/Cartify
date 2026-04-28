package com.cartify.ecommerce.authorization.permission.application.query;

import com.cartify.ecommerce.authorization.permission.application.response.PermissionResponse;
import com.cartify.ecommerce.authorization.permission.domain.Permission;
import com.cartify.ecommerce.authorization.permission.domain.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.QueryModel;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Query handler for retrieving permission information.
 *
 * <p>This handler processes permission queries and returns corresponding
 * permission responses from the repository.</p>
 */
@Service
@QueryModel
@RequiredArgsConstructor
public class PermissionsQueryHandler {

    private final PermissionRepository repository;

    /**
     * Handles permission queries and returns the corresponding permissions as responses.
     *
     * @param query the permission query to process
     * @return a list of permission responses matching the query criteria
     */
    @Transactional(readOnly = true)
    public @NonNull List<PermissionResponse> handle(@NonNull PermissionQuery query) {
        List<Permission> permissions = switch (query) {
            case PermissionQuery.All() -> this.repository.findAll();
            case PermissionQuery.ByModule(String module) -> this.repository.findAllByModule(module);
        };

        return permissions.stream()
                .map(PermissionResponse::from)
                .toList();
    }
}
