CREATE MATERIALIZED VIEW category_full_view AS
SELECT
    c.id,
    c.name,
    c.slug,
    c.image_url,
    c.status,
    c.display_order,
    c.parent_id,
    cm.description,
    cm.seo_title,
    cm.seo_description
FROM categories c
         LEFT JOIN categories_meta cm ON cm.category_id = c.id;

CREATE UNIQUE INDEX category_full_view_id_idx ON category_full_view (id);
CREATE INDEX category_full_view_slug_idx ON category_full_view (slug);
CREATE INDEX category_full_view_status_idx ON category_full_view (status);
CREATE INDEX category_full_view_parent_idx ON category_full_view (parent_id);