CREATE MATERIALIZED VIEW category_full_view AS
SELECT c.id,
       c.name,
       c.slug,
       c.image_url,
       c.status,
       c.display_order,
       cm.description,
       cm.seo_title,
       cm.seo_description,
       c.parent_id,
       COALESCE(
                       json_agg(
                       json_build_object('id', ch.id, 'name', ch.name)
                       ORDER BY ch.display_order, ch.id
                               ) FILTER (WHERE ch.id IS NOT NULL),
                       '[]'
       ) AS children
FROM categories c
         LEFT JOIN categories_meta cm ON cm.category_id = c.id
         LEFT JOIN categories ch ON ch.parent_id = c.id
WHERE c.parent_id IS NULL
GROUP BY c.id, c.name, c.slug, c.image_url, c.status,
         c.display_order, cm.description, cm.seo_title, cm.seo_description, c.parent_id;

CREATE UNIQUE INDEX category_full_view_id_idx ON category_full_view (id);
CREATE INDEX category_full_view_slug_idx ON category_full_view (slug);
CREATE INDEX category_full_view_status_idx ON category_full_view (status);