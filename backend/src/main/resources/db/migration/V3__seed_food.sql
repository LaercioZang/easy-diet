INSERT INTO food_category (id, name, code, active) VALUES
(gen_random_uuid(), 'Protein', 'PROTEIN', true),
(gen_random_uuid(), 'Carbohydrate', 'CARB', true),
(gen_random_uuid(), 'Fat', 'FAT', true);


INSERT INTO food (
    id, name, category_id,
    calories,
    protein,
    carbs,
    fat,
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
