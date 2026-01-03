INSERT INTO food_category (id, name) VALUES
(gen_random_uuid(), 'Protein'),
(gen_random_uuid(), 'Carbohydrate'),
(gen_random_uuid(), 'Fat');


INSERT INTO food (
    id, name, category_id,
    calories_per_100g,
    protein_per_100g,
    carbs_per_100g,
    fat_per_100g,
    active
)
SELECT
    gen_random_uuid(),
    'Chicken Breast',
    c.id,
    165,
    31.0,
    0.0,
    3.6,
    true
FROM food_category c
WHERE c.name = 'Protein';
