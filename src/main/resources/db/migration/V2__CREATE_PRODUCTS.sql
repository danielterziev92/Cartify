CREATE TABLE products
(
    id          UUID           NOT NULL,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    base_price  DECIMAL(10, 2) NOT NULL,
    status      VARCHAR(255)   NOT NULL,
    category_id BIGINT         NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

CREATE INDEX product_category_status_idx ON products (category_id, status);
CREATE INDEX product_name_idx ON products (name);
CREATE INDEX product_status_idx ON products (status);
CREATE INDEX product_category_idx ON products (category_id);