-- =========================
-- DIET PLAN
-- =========================
CREATE INDEX IF NOT EXISTS idx_diet_plan_user_status
    ON diet_plan (user_id, status);

CREATE INDEX IF NOT EXISTS idx_diet_plan_user_created_at
    ON diet_plan (user_id, created_at DESC);

-- =========================
-- MEAL
-- =========================
CREATE INDEX IF NOT EXISTS idx_meal_diet_plan
    ON meal (diet_plan_id);

CREATE INDEX IF NOT EXISTS idx_meal_diet_plan_day
    ON meal (diet_plan_id, day_of_week);

CREATE INDEX IF NOT EXISTS idx_meal_diet_plan_day_order
    ON meal (diet_plan_id, day_of_week, meal_order);

-- =========================
-- FOOD ITEM
-- =========================
CREATE INDEX IF NOT EXISTS idx_food_item_meal
    ON food_item (meal_id);

-- =========================
-- FOOD
-- =========================
CREATE INDEX IF NOT EXISTS idx_food_category_active
    ON food (category_id, active);

-- =========================
-- REFRESH TOKEN
-- =========================
CREATE INDEX IF NOT EXISTS idx_refresh_token_user
    ON refresh_token (user_id);
