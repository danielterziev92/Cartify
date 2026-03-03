CREATE SEQUENCE IF NOT EXISTS variant_option_value_sequence START WITH 1 INCREMENT BY 10;

CREATE TABLE variant_option_values
(
    id              BIGINT DEFAULT nextval('variant_option_value_sequence') NOT NULL,
    variant_id      BIGINT                                                  NOT NULL,
    option_value_id BIGINT                                                  NOT NULL,
    CONSTRAINT pk_variant_option_values PRIMARY KEY (id)
);

ALTER TABLE variant_option_values
    ADD CONSTRAINT uk_variant_option_value UNIQUE (variant_id, option_value_id);

ALTER TABLE variant_option_values
    ADD CONSTRAINT FK_VARIANT_OPTION_VALUES_ON_OPTION_VALUE FOREIGN KEY (option_value_id) REFERENCES option_values (id);

ALTER TABLE variant_option_values
    ADD CONSTRAINT FK_VARIANT_OPTION_VALUES_ON_VARIANT FOREIGN KEY (variant_id) REFERENCES product_variants (id);

CREATE INDEX variant_option_value_option_value_idx ON variant_option_values (option_value_id);

CREATE INDEX variant_option_value_variant_idx ON variant_option_values (variant_id);