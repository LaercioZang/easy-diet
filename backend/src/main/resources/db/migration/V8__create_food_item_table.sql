CREATE TABLE food_item (
    id UUID PRIMARY KEY,
    meal_id UUID NOT NULL,
    food_id UUID NOT NULL,

    quantity NUMERIC(10,2) NOT NULL,

    calories NUMERIC(10,2) NOT NULL,
    protein  NUMERIC(10,2) NOT NULL,
    carbs    NUMERIC(10,2) NOT NULL,
    fat      NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_food_item_meal
        FOREIGN KEY (meal_id)
        REFERENCES meal(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_food_item_food
        FOREIGN KEY (food_id)
        REFERENCES food(id)
);
