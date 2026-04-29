package com.cartify.ecommerce.authorization.permission.application;

import com.cartify.ecommerce.authorization.permission.domain.Permission;
import com.cartify.ecommerce.authorization.permission.domain.PermissionBit;
import com.cartify.ecommerce.authorization.permission.domain.PermissionEvent;
import com.cartify.ecommerce.authorization.permission.domain.PermissionRepository;
import com.cartify.ecommerce.shared.annotation.PermissionModule;
import com.cartify.ecommerce.shared.annotation.PermissionResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.modulith.NamedInterface;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Component responsible for registering and synchronizing permission definitions
 * with the database. It scans for permission declarations in the codebase and
 * ensures that the database contains up-to-date permission records.
 *
 * <p>This registrar performs a synchronization process during application startup
 * that compares declared permissions with existing database records and creates or
 * removes permissions as needed to maintain consistency.</p>
 */
@NamedInterface
@Slf4j
@Component
@RequiredArgsConstructor
public class PermissionRegistrar implements ApplicationRunner {

    private final PermissionRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${spring.application.base-package}")
    private String basePackage;

    /**
     * Synchronizes permission definitions with the database.
     *
     * <p>This method runs during application startup and performs a complete
     * synchronization of permission records. It scans for declared permissions,
     * compares them with existing database records, and creates or removes
     * permissions as necessary to maintain consistency. The operation is wrapped
     * in a transaction to ensure data integrity.</p>
     *
     * @param args command line arguments (not used)
     * @throws Exception if permission synchronization fails
     */
    @Override
    @Transactional
    public void run(@NonNull ApplicationArguments args) throws Exception {
        log.info("Starting permission sync...");

        Map<String, Permission> permissions = this.repository.findAll()
                .stream()
                .collect(Collectors.toMap(this::permissionKey, Function.identity()));

        Map<String, Permission> declared = this.scanDeclaredPermissions()
                .stream()
                .collect(Collectors.toMap(
                        this::permissionKey,
                        Function.identity(),
                        (a, b) -> {
                            log.warn("Duplicate permission declaration for [{}] — keeping first.", permissionKey(a));
                            return a;
                        }
                ));

        Set<String> permissionKeys = permissions.keySet();
        Set<String> declaredKeys = declared.keySet();

        List<Permission> toCreate = declaredKeys.stream()
                .filter(key -> !permissionKeys.contains(key))
                .map(declared::get)
                .toList();

        List<Permission> toDelete = permissionKeys.stream()
                .filter(key -> !declaredKeys.contains(key))
                .map(permissions::get)
                .toList();

        if (!toCreate.isEmpty()) {
            this.repository.saveAll(toCreate);
            log.info("Created {} new permissions.", toCreate.size());
            toCreate.forEach(permission -> log.info("Created permission: [{}]", permission.fullPath()));
        }

        if (!toDelete.isEmpty()) {
            toDelete.forEach(permission -> this.eventPublisher.publishEvent(
                    new PermissionEvent.Removed(permission.module(), permission.resource())
            ));

            this.repository.deleteAll(toDelete);
            log.info("Deleted {} old permissions.", toDelete.size());
            toDelete.forEach(permission -> log.info("Permission removed: [{}]", permission.fullPath()));
        }

        log.info("Permission sync complete — created: {}, removed: {}, unchanged: {}.",
                toCreate.size(),
                toDelete.size(),
                permissionKeys.size() - toDelete.size());
    }

    /**
     * Scans the application's classpath for permission declarations.
     *
     * <p>This method uses classpath scanning to find all classes annotated with
     * {@code @PermissionModule} and extracts permission definitions from them.
     * It searches within the configured base package to find all relevant
     * permission declarations.</p>
     *
     * @return list of permission objects found in the scanned classes
     */
    private @NonNull List<Permission> scanDeclaredPermissions() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(PermissionModule.class));

        List<Permission> permissions = new ArrayList<>();

        scanner.findCandidateComponents(this.basePackage).forEach(beanDefinition -> {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                permissions.addAll(extractPermissions(clazz));
            } catch (ClassNotFoundException e) {
                log.warn("Could not load class [{}]: {}", beanDefinition.getBeanClassName(), e.getMessage());
            }
        });

        return permissions;
    }

    /**
     * Extracts permission definitions from a class that is annotated with @PermissionModule.
     *
     * <p>This method examines all fields in the provided class for {@code @PermissionResource}
     * annotations. It validates that these fields are of type int and extracts the
     * module name from the class's {@code @PermissionModule} annotation to construct
     * permission objects.</p>
     *
     * @param clazz the class to scan for permission declarations
     * @return list of permission objects extracted from the class fields
     */
    private @NonNull List<Permission> extractPermissions(@NonNull Class<?> clazz) {
        List<Permission> permissions = new ArrayList<>();

        PermissionModule annotation = clazz.getAnnotation(PermissionModule.class);
        if (annotation == null) return permissions;

        String module = annotation.value();

        for (Field field : clazz.getDeclaredFields()) {
            PermissionResource resourceAnnotation = field.getAnnotation(PermissionResource.class);
            if (resourceAnnotation == null) continue;

            if (!int.class.equals(field.getType())) {
                log.warn("Field [{}.{}] annotated with @PermissionResource is not an int — skipped.",
                        clazz.getSimpleName(), field.getName());
                continue;
            }

            field.setAccessible(true);
            int mask = PermissionBit.NONE.value;
            String resource = resourceAnnotation.value().isBlank() ? null : resourceAnnotation.value();

            permissions.add(Permission.of(module, resource, mask));
        }

        return permissions;
    }

    /**
     * Generates a unique key for a permission based on its module and mask.
     *
     * <p>This method creates a string key that uniquely identifies a permission
     * by combining its module name with the mask value. This key is used to
     * compare permissions between database records and declared definitions.</p>
     *
     * @param permission the permission to generate a key for
     * @return a unique string key for the permission
     */
    private @NonNull String permissionKey(@NonNull Permission permission) {
        return permission.module() + ":" + Objects.toString(permission.mask(), "");
    }
}
