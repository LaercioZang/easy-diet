-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =========================
-- DIET TYPE (catalog)
-- =========================
CREATE TABLE diet_type (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true
);

-- =========================
-- FOOD CATEGORY (catalog)
-- =========================
CREATE TABLE food_category (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true
);

-- =========================
-- FOOD (base food per 100g)
-- =========================
CREATE TABLE food (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,

    category_id UUID NOT NULL,
    CONSTRAINT fk_food_category
        FOREIGN KEY (category_id)
        REFERENCES food_category (id),

    calories DECIMAL(6,2) NOT NULL,
    protein DECIMAL(6,2) NOT NULL,
    carbs DECIMAL(6,2) NOT NULL,
    fat DECIMAL(6,2) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT true
);

-- =========================
-- DIET (generated diet)
-- =========================
CREATE TABLE diet (
    id UUID PRIMARY KEY,
    diet_type_id UUID NOT NULL,

    objective VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,

    target_calories INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT fk_diet_type
        FOREIGN KEY (diet_type_id)
        REFERENCES diet_type (id)
);

-- =========================
-- USER PROFILE (snapshot)
-- =========================
CREATE TABLE user_profile (
    id UUID PRIMARY KEY,
    diet_id UUID NOT NULL UNIQUE,

    weight DECIMAL(10,2) NOT NULL,
    height DECIMAL(10,2) NOT NULL,
    age INTEGER,
    sex VARCHAR(20),

    CONSTRAINT fk_user_profile_diet
        FOREIGN KEY (diet_id)
        REFERENCES diet (id)
        ON DELETE CASCADE
);

-- =========================
-- WEEK PLAN
-- =========================
CREATE TABLE week_plan (
    id UUID PRIMARY KEY,
    diet_id UUID NOT NULL UNIQUE,

    total_calories INTEGER NOT NULL,
    total_protein DECIMAL(6,2) NOT NULL,
    total_carbs DECIMAL(6,2) NOT NULL,
    total_fat DECIMAL(6,2) NOT NULL,

    CONSTRAINT fk_week_plan_diet
        FOREIGN KEY (diet_id)
        REFERENCES diet (id)
        ON DELETE CASCADE
);

-- =========================
-- DAY PLAN
-- =========================
CREATE TABLE day_plan (
    id UUID PRIMARY KEY,
    week_plan_id UUID NOT NULL,
    day_of_week INTEGER NOT NULL,

    total_calories INTEGER NOT NULL,
    total_protein DECIMAL(6,2) NOT NULL,
    total_carbs DECIMAL(6,2) NOT NULL,
    total_fat DECIMAL(6,2) NOT NULL,

    CONSTRAINT fk_day_plan_week
        FOREIGN KEY (week_plan_id)
        REFERENCES week_plan (id)
        ON DELETE CASCADE
);

-- =========================
-- MEAL PLAN
-- =========================
CREATE TABLE meal_plan (
    id UUID PRIMARY KEY,
    day_plan_id UUID NOT NULL,

    meal_type VARCHAR(50) NOT NULL,

    calories INTEGER NOT NULL,
    protein DECIMAL(6,2) NOT NULL,
    carbs DECIMAL(6,2) NOT NULL,
    fat DECIMAL(6,2) NOT NULL,

    CONSTRAINT fk_meal_plan_day
        FOREIGN KEY (day_plan_id)
        REFERENCES day_plan (id)
        ON DELETE CASCADE
);

-- =========================
-- MEAL FOOD (junction)
-- =========================
CREATE TABLE meal_food (
    id UUID PRIMARY KEY,
    meal_plan_id UUID NOT NULL,
    food_id UUID NOT NULL,

    grams DECIMAL(6,2) NOT NULL,
    calories INTEGER NOT NULL,
    protein DECIMAL(6,2) NOT NULL,
    carbs DECIMAL(6,2) NOT NULL,
    fat DECIMAL(6,2) NOT NULL,

    CONSTRAINT fk_meal_food_meal
        FOREIGN KEY (meal_plan_id)
        REFERENCES meal_plan (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_meal_food_food
        FOREIGN KEY (food_id)
        REFERENCES food (id)
);
