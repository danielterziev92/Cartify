-- ============================================================
-- Seed 35 categories with nesting
--
-- Structure:
--   Root (10):        id 1-10
--   Under Electronics (id=1): id 11-16  (6 children)
--   Under Fashion (id=2):     id 17-22  (6 children)
--   Under Home & Garden(id=3):id 23-27  (5 children)
--   Under Sports (id=4):      id 28-31  (4 children)
--   Under Smartphones (id=11):id 32-33  (2 children, same parent)
--   Under Laptops (id=12):    id 34-35  (2 children, same parent)
--
-- Total: 10 + 6 + 6 + 5 + 4 + 2 + 2 = 35
-- ============================================================

INSERT INTO categories (id, name, slug, image_url, status, display_order, parent_id)
VALUES

-- ── Root categories ───────────────────────────────────────────
(1,  'Electronics',    'electronics',    'https://example.com/img/electronics.jpg',   'ACTIVE',   1,  NULL),
(2,  'Fashion',        'fashion',        'https://example.com/img/fashion.jpg',        'ACTIVE',   2,  NULL),
(3,  'Home & Garden',  'home-garden',    'https://example.com/img/home-garden.jpg',    'ACTIVE',   3,  NULL),
(4,  'Sports',         'sports',         'https://example.com/img/sports.jpg',         'ACTIVE',   4,  NULL),
(5,  'Books',          'books',          'https://example.com/img/books.jpg',          'ACTIVE',   5,  NULL),
(6,  'Beauty',         'beauty',         'https://example.com/img/beauty.jpg',         'ACTIVE',   6,  NULL),
(7,  'Food & Drinks',  'food-drinks',    'https://example.com/img/food.jpg',           'ACTIVE',   7,  NULL),
(8,  'Automotive',     'automotive',     'https://example.com/img/automotive.jpg',     'ACTIVE',   8,  NULL),
(9,  'Toys',           'toys',           'https://example.com/img/toys.jpg',           'ACTIVE',   9,  NULL),
(10, 'Health',         'health',         'https://example.com/img/health.jpg',         'DRAFT',    10, NULL),

-- ── Children of Electronics (id=1) ───────────────────────────
(11, 'Smartphones',    'smartphones',    'https://example.com/img/smartphones.jpg',    'ACTIVE',   1,  1),
(12, 'Laptops',        'laptops',        'https://example.com/img/laptops.jpg',        'ACTIVE',   2,  1),
(13, 'Tablets',        'tablets',        'https://example.com/img/tablets.jpg',        'ACTIVE',   3,  1),
(14, 'Cameras',        'cameras',        'https://example.com/img/cameras.jpg',        'ACTIVE',   4,  1),
(15, 'Audio',          'audio',          'https://example.com/img/audio.jpg',          'ACTIVE',   5,  1),
(16, 'Gaming',         'gaming',         'https://example.com/img/gaming.jpg',         'ACTIVE',   6,  1),

-- ── Children of Fashion (id=2) ───────────────────────────────
(17, 'Men''s Clothing', 'mens-clothing', 'https://example.com/img/mens.jpg',           'ACTIVE',   1,  2),
(18, 'Women''s Clothing','womens-clothing','https://example.com/img/womens.jpg',        'ACTIVE',   2,  2),
(19, 'Shoes',           'shoes',          'https://example.com/img/shoes.jpg',          'ACTIVE',   3,  2),
(20, 'Accessories',     'accessories',    'https://example.com/img/accessories.jpg',    'ACTIVE',   4,  2),
(21, 'Watches',         'watches',        'https://example.com/img/watches.jpg',        'ACTIVE',   5,  2),
(22, 'Jewelry',         'jewelry',        'https://example.com/img/jewelry.jpg',        'DRAFT',    6,  2),

-- ── Children of Home & Garden (id=3) ─────────────────────────
(23, 'Furniture',       'furniture',      'https://example.com/img/furniture.jpg',      'ACTIVE',   1,  3),
(24, 'Kitchen',         'kitchen',        'https://example.com/img/kitchen.jpg',        'ACTIVE',   2,  3),
(25, 'Garden Tools',    'garden-tools',   'https://example.com/img/garden.jpg',         'ACTIVE',   3,  3),
(26, 'Bedding',         'bedding',        'https://example.com/img/bedding.jpg',        'ACTIVE',   4,  3),
(27, 'Lighting',        'lighting',       'https://example.com/img/lighting.jpg',       'DRAFT',    5,  3),

-- ── Children of Sports (id=4) ────────────────────────────────
(28, 'Fitness Equipment','fitness',       'https://example.com/img/fitness.jpg',        'ACTIVE',   1,  4),
(29, 'Outdoor Sports',  'outdoor-sports', 'https://example.com/img/outdoor.jpg',        'ACTIVE',   2,  4),
(30, 'Team Sports',     'team-sports',    'https://example.com/img/team-sports.jpg',    'ACTIVE',   3,  4),
(31, 'Water Sports',    'water-sports',   'https://example.com/img/water-sports.jpg',   'DRAFT',    4,  4),

-- ── Children of Smartphones (id=11) — two siblings ───────────
-- Both share the same parent (Smartphones), demonstrating shared parent
(32, 'iPhone Cases',    'iphone-cases',   'https://example.com/img/iphone-cases.jpg',  'ACTIVE',   1,  11),
(33, 'Android Accessories','android-acc', 'https://example.com/img/android.jpg',       'ACTIVE',   2,  11),

-- ── Children of Laptops (id=12) — two siblings ───────────────
-- Both share the same parent (Laptops), demonstrating shared parent
(34, 'Gaming Laptops',  'gaming-laptops', 'https://example.com/img/gaming-laptops.jpg','ACTIVE',   1,  12),
(35, 'Business Laptops','business-laptops','https://example.com/img/biz-laptops.jpg',  'ACTIVE',   2,  12);

-- Reset sequence so next auto-generated id starts after 35
SELECT setval('categories_id_seq', 35);

REFRESH MATERIALIZED VIEW CONCURRENTLY category_full_view