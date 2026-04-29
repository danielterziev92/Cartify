CREATE SCHEMA IF NOT EXISTS auth;

ALTER DATABASE "cartify-db" SET search_path TO auth;

CREATE TABLE auth.permissions
(
    id       UUID         NOT NULL DEFAULT gen_random_uuid(),
    module   VARCHAR(100) NOT NULL,
    resource VARCHAR(200),
    mask     INTEGER      NOT NULL,

    CONSTRAINT pk_permissions
        PRIMARY KEY (id),

    CONSTRAINT uq_permissions_module
        UNIQUE (module),

    CONSTRAINT uq_permissions_module_resource
        UNIQUE (module, resource),

    CONSTRAINT chk_permissions_module_not_blank
        CHECK ( TRIM(module) <> '' ),

    CONSTRAINT chk_permissions_mask_not_negative
        CHECK ( mask >= 0 )
);

CREATE INDEX idx_permissions_module_resource
    ON auth.permissions (module, resource);