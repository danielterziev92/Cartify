CREATE SEQUENCE IF NOT EXISTS product_variant_sequence START WITH 1 INCREMENT BY 10;

CREATE TABLE product_variants
(
    id               BIGINT DEFAULT nextval('product_variant_sequence') NOT NULL,
    sku              VARCHAR(50),
    additional_price DECIMAL(10, 2)                                     NOT NULL,
    quantity         INTEGER                                            NOT NULL,
    product_id       UUID                                               NOT NULL,
    CONSTRAINT pk_product_variants PRIMARY KEY (id)
);

ALTER TABLE product_variants
    ADD CONSTRAINT uc_product_variants_sku UNIQUE (sku);

ALTER TABLE product_variants
    ADD CONSTRAINT FK_PRODUCT_VARIANTS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

CREATE INDEX product_variant_sku_idx ON product_variants (sku);

CREATE INDEX product_variant_product_idx ON product_variants (product_id);