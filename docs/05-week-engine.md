# Easy Diet — Weekly Planning Engine

## Purpose of the Weekly Planning Engine

The Weekly Planning Engine is responsible for **structuring the diet across an entire week**, transforming nutritional targets into a practical, day-by-day plan.

Its responsibilities include:
- Creating a weekly structure
- Differentiating training and rest days
- Redistributing calories across the week
- Creating daily meal structures
- Preparing the plan for user customization

This engine does **not** decide macros.  
It **consumes outputs from the Nutrition Engine** and applies them over time.

---

## Core Responsibilities

The Weekly Planning Engine must:

- Respect the user’s training routine
- Preserve total weekly calorie targets
- Adjust daily calories based on activity
- Create a consistent and editable structure
- Remain deterministic and predictable

---

## Inputs

The engine receives the following inputs:

- Nutrition target (calories and macros)
- Diet type
- Training days of the week
- Number of meals per day
- User preferences already validated upstream

---

## Weekly Calorie Perspective

The engine always thinks **in terms of the full week first**, not isolated days.

Example:
Daily target: 2600 kcal
Weekly total: 18,200 kcal


This prevents inconsistencies when training and rest days vary.

---

## Training vs Rest Days

Each day of the week is classified as:

- **Training day**
- **Rest day**

This classification drives calorie redistribution.

---

## Calorie Redistribution Logic

The total weekly calories remain constant.

Typical redistribution:
- Training days: +5% to +10% calories
- Rest days: −5% to −10% calories

Example:
Training day: 2700 kcal
Rest day: 2300 kcal

Exact percentages may evolve without breaking the interface.

---

## Daily Plan Creation

For each day of the week, the engine creates a `DayPlan` containing:

- Day of the week
- Training flag
- Daily calorie target
- List of meals

The engine does not yet assign foods.

---

## Meal Structure Generation

Meals are created based on the user-defined number of meals per day.

Example:
4 meals/day
→ Breakfast
→ Lunch
→ Snack
→ Dinner


Each meal starts as a **structural container**, not a finalized composition.

---

## Separation of Concerns

The Weekly Planning Engine:

- Does NOT select foods
- Does NOT calculate macros
- Does NOT apply diet rules

Its only goal is **temporal and structural organization**.

Food selection and macro distribution happen later in the pipeline.

---

## Immutability and Re-generation

Weekly plans are treated as **snapshots**.

When:
- The diet type changes
- The goal changes
- The training routine changes

A **new weekly plan is generated**, preserving historical data.

---

## User Interaction Compatibility

The structure created by the engine is designed to be:

- Fully editable by the user
- Compatible with automatic adjustments
- Stable under frequent modifications

The engine ensures that any user change can be safely absorbed later by the adjustment logic.

---

## Error Handling

The Weekly Planning Engine is designed to be fail-safe:

- Invalid training schedules are rejected upstream
- Missing inputs result in no plan generation
- Errors are explicit and deterministic

---

## Extensibility

The engine supports future extensions such as:

- Different calorie distribution strategies
- Special training days
- Deload or rest weeks
- Periodization logic

Without requiring changes to its core contract.

---

## Summary

The Weekly Planning Engine transforms nutritional targets into a **practical weekly structure**.

It ensures that:
- Calories are distributed logically
- Training intensity is respected
- The plan remains flexible and editable
- The system stays predictable and stable

This engine acts as the **bridge between nutrition logic and real-life scheduling**.
