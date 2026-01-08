CREATE TABLE meal (
     id UUID PRIMARY KEY,

     diet_plan_id UUID NOT NULL,

     day_of_week VARCHAR(20) NOT NULL,

     name VARCHAR(100) NOT NULL,

     meal_order INTEGER NOT NULL,

     /* ====== TOTALS (AGGREGATED STATE) ====== */

     total_calories NUMERIC(10,2) NOT NULL DEFAULT 0,
     total_protein  NUMERIC(10,2) NOT NULL DEFAULT 0,
     total_carbs    NUMERIC(10,2) NOT NULL DEFAULT 0,
     total_fat      NUMERIC(10,2) NOT NULL DEFAULT 0,

     /* ====== AUDIT ====== */

     created_at TIMESTAMP NOT NULL,

     CONSTRAINT fk_meal_diet_plan
         FOREIGN KEY (diet_plan_id)
         REFERENCES diet_plan(id)
         ON DELETE CASCADE
);
