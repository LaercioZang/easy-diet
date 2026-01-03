# Easy Diet — Nutrition Engine

## Purpose of the Nutrition Engine

The Nutrition Engine is the **core decision-making component** of Easy Diet.

Its responsibility is to:
- Calculate calories and macronutrients
- Enforce diet-specific rules
- Maintain nutritional balance
- Automatically adjust plans after user changes

The engine operates entirely on the backend and is independent of UI, database, or framework concerns.

---

## Core Principles

The Nutrition Engine follows these principles:

- Users never calculate macros manually
- Protein intake is always prioritized
- Adjustments happen automatically
- Diet constraints are strictly enforced
- The system favors consistency over precision

---

## Inputs

The engine receives the following inputs:

- User profile (sex, age, height, weight)
- User goal (bulk or cut)
- Selected diet type
- Training routine
- Number of meals per day

These inputs are already validated before reaching the engine.

---

## Calorie Calculation

Calories are calculated based on:
- Basal metabolic considerations
- Activity level inferred from training days
- User goal (bulk or cut)

The exact formula is abstracted from the user and may evolve over time without affecting the interface.

---

## Macronutrient Distribution

### Protein
- Calculated based on body weight
- Fixed priority across all diet types
- Reduced only as a last resort

### Fat
- Maintained within a healthy range
- Acts as a secondary adjustment lever
- Increased or decreased depending on diet type

### Carbohydrates
- Fully adjustable in flexible diets
- Restricted or capped in low-carb diets
- Never used to compensate violations in ketogenic diets

---

## Macro Adjustment Priority

Whenever an adjustment is required, the engine follows this strict order:

1. Carbohydrates
2. Fat
3. Protein (only if unavoidable)

This ensures muscle preservation and metabolic stability.

---

## Diet-Specific Constraints

Each diet type applies additional constraints:

- Normal: no hard limits
- Vegetarian/Vegan: increased protein targets
- Carnivore: near-zero carbohydrates
- Ketogenic: strict carbohydrate cap
- Jungle Diet: carbohydrates allowed only from natural sources

These constraints are enforced centrally by the engine.

---

## Automatic Rebalancing

Whenever a user:
- Replaces a food
- Changes a portion size
- Removes a meal item

The engine:
1. Recalculates the affected meal
2. Evaluates daily macro deviation
3. Redistributes macros across remaining meals
4. Validates diet constraints
5. Applies corrective adjustments

All of this happens without user intervention.

---

## Error Handling

If a user action violates diet constraints:

- The engine blocks invalid food choices when necessary
- Clear and non-technical feedback is returned
- The user is informed without being restricted unnecessarily

The goal is guidance, not punishment.

---

## Independence from Persistence

The Nutrition Engine:
- Does not know how data is stored
- Does not perform database operations
- Does not depend on ORM or repositories

It operates purely on domain objects.

---

## Extensibility

The engine is designed to:
- Support new diet types
- Introduce new adjustment rules
- Evolve calorie formulas
- Enable feature flags and experiments

All without breaking existing behavior.

---

## Summary

The Nutrition Engine ensures that:

- Users have full dietary freedom
- Nutritional balance is always preserved
- Diet-specific rules are respected
- Adjustments are seamless and automatic

The engine is the foundation upon which the entire Easy Diet experience is built.
