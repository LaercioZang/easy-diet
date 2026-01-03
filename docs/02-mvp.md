# Easy Diet — MVP Definition

## Objective of the MVP

The goal of the Easy Diet MVP is to **validate the core value of the product**:

> Automatically generate and continuously adjust a weekly diet plan based on the user’s goal, training routine, diet type, and food choices — without manual calculations.

If this works well, the product works.

---

## What the MVP Must Prove

The MVP must demonstrate that:

- The nutrition engine can generate a coherent weekly plan
- The plan adapts to training and rest days
- Users can freely edit meals without breaking nutritional balance
- The system automatically rebalances macros after changes
- Different diet types are respected by design

---

## Features Included in the MVP

### 1. User Onboarding
- Basic personal data (sex, age, height, weight)
- Goal selection (bulk or cut)
- Diet type selection
- Training days selection
- Number of meals per day

---

### 2. Supported Diet Types
The MVP supports the following diet types:

- Normal
- Vegetarian
- Vegan
- Carnivore
- Ketogenic
- Jungle Diet (Easy Diet proprietary model)

Each diet type defines:
- Allowed foods
- Restricted foods
- Macro constraints
- Adjustment rules

---

### 3. Nutrition Engine (Core)
- Calorie calculation based on goal
- Macro distribution (protein, carbs, fat)
- Protein as a fixed priority
- Automatic adjustments after user edits
- Rules enforced per diet type

This engine runs entirely on the backend.

---

### 4. Weekly Planning Engine
- Automatic generation of weekly plans
- Training vs rest day differentiation
- Calorie redistribution across the week
- Meal structure creation per day

---

### 5. Editable Diet Plan
- Users can replace foods
- Users can adjust quantities
- Users can freely customize meals
- System recalculates macros automatically

The user controls *choices*, the system controls *balance*.

---

### 6. Food Base
- Read-only official food database (TACO)
- User-created custom foods
- Food filtering based on diet type

---

### 7. Weekly Check-in
- User inputs current weight
- System evaluates progress
- Automatic calorie adjustment when needed

---

### 8. Basic History
- Weight history
- Active diet type history
- Calorie targets over time

---

## What Is Explicitly Out of Scope

The following features are intentionally **excluded** from the MVP:

- Recipe instructions
- Barcode scanning
- Daily calorie logging
- Social features
- Gamification
- AI chat or conversational onboarding
- Wearable integrations
- Multiple advanced diet methodologies beyond the defined set

---

## Platform Scope

The MVP is designed as a **mobile application**, with backend support.

Target platforms after completion:
- iOS (Apple App Store)
- Android (Google Play Store)

There is no web interface in the MVP.

---

## Non-Goals of the MVP

- Not a calorie tracker
- Not a recipe app
- Not a social fitness platform
- Not a meal delivery solution

The focus is strictly on **planning, adjustment, and flexibility**.

---

## MVP Success Criteria

The MVP is considered successful if:

- A user can generate a weekly diet in under one minute
- Any meal can be edited without breaking the plan
- The system always keeps macros within acceptable limits
- The user understands the impact of changes without seeing calculations

---

## Summary

The Easy Diet MVP is intentionally minimal and focused.

It exists to validate:
- the nutrition engine
- the weekly planning logic
- the user experience of flexibility with safety

Everything else comes later.
