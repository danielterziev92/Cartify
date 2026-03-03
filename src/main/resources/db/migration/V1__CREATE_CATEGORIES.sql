CREATE TABLE categories
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    name      VARCHAR(255)                        NOT NULL,
    parent_id BIGINT,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_PARENT FOREIGN KEY (parent_id) REFERENCES categories (id);

CREATE UNIQUE INDEX category_name_idx ON categories (name);
CREATE INDEX category_parent_idx ON categories (parent_id);