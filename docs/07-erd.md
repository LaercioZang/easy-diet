# Easy Diet — Data Model (ERD)

## Purpose of This Document

This document defines the **conceptual data model** of Easy Diet.

It describes the main entities, their relationships, and important constraints, without tying the model to a specific database or ORM implementation.

The goal is to ensure:
- Data consistency
- Clear ownership of information
- Safe historical tracking
- Future scalability

---

## Core Modeling Principles

The data model follows these principles:

- Historical data is immutable
- Configuration data is reusable
- User edits create new versions, not mutations
- The nutrition engine remains independent of persistence
- The database supports the engine, not the opposite

---

## Main Entities Overview

User
└── UserProfile
└── NutritionTarget
└── DietType
└── WeekPlan
└── DayPlan
└── Meal
└── MealFood
└── Food
└── CheckIn


---

## Entity Definitions

### User
Represents the application user.

Notes:
- Authentication details are managed separately (OAuth2)
- The domain focuses on nutrition-related data only

---

### UserProfile
Stores biological and physical user attributes.

Attributes include:
- Sex
- Age
- Height
- Current weight

This data is used as input for nutritional calculations.

---

### NutritionTarget
Represents the **active nutritional goal** at a given time.

Attributes include:
- Daily calories
- Protein, carbohydrate, and fat targets
- Goal type (bulk or cut)

A new NutritionTarget is created whenever:
- The goal changes
- The system adjusts calories after a check-in

---

### DietType
Represents a supported diet configuration.

Characteristics:
- Stored as reference data
- Can be activated or deactivated
- Not hardcoded as enums

Diet rules are not stored in this entity.

---

### Food
Represents a nutritional food item.

Characteristics:
- May come from an official food database (e.g. TACO)
- May be user-created (custom food)
- Stores macro values per standard unit

Official foods are read-only.

---

### WeekPlan
Represents a **snapshot of a weekly diet plan**.

Characteristics:
- Associated with a specific NutritionTarget
- Linked to a DietType
- Immutable once created

Any major change generates a new WeekPlan.

---

### DayPlan
Represents a single day inside a WeekPlan.

Attributes include:
- Day of the week
- Training or rest indicator
- Daily calorie target

---

### Meal
Represents a meal within a day.

Characteristics:
- Structural container
- Editable by the user
- Does not store calculated macros

---

### MealFood
Represents the association between a food and its quantity.

Characteristics:
- Stores food reference
- Stores quantity in grams
- Allows macro calculation at runtime

---

### CheckIn
Represents a weekly user check-in.

Attributes include:
- Date
- Body weight

Check-ins are used to evaluate progress and trigger adjustments.

---

## Relationship Summary

- One User has one UserProfile
- One User can have many NutritionTargets over time
- One DietType can be associated with many WeekPlans
- One WeekPlan contains multiple DayPlans
- One DayPlan contains multiple Meals
- One Meal contains multiple MealFoods
- One Food can be referenced by many MealFoods
- One User can have many CheckIns

---

## Historical Data Strategy

The following entities are treated as **immutable snapshots**:

- NutritionTarget
- WeekPlan
- DayPlan
- Meal
- MealFood

Edits result in new versions, not in-place updates.

---

## Database Considerations (Future)

When persisted:
- PostgreSQL is the recommended database
- DietType and Food are seeded tables
- WeekPlans grow over time but remain queryable
- Indexing focuses on user and date-based queries

ORM-specific details (JPA annotations) are intentionally omitted.

---

## Summary

The Easy Diet data model is designed to:

- Preserve history
- Support flexibility
- Avoid accidental data corruption
- Scale with new diet types and rules

The model serves the engine, ensuring the system remain
