-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE diet_plan (
    id UUID PRIMARY KEY,
    tdee INTEGER NOT NULL,
    week_distribution_json TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    user_id UUID NOT NULL
);

ALTER TABLE diet_plan
ADD CONSTRAINT fk_diet_plan_user
FOREIGN KEY (user_id) REFERENCES app_user(id);
