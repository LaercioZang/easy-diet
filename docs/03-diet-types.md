# Easy Diet — Diet Types Definition

## Purpose of This Document

This document defines the **official diet types supported by Easy Diet** and the rules associated with each one.

Each diet type determines:
- Which foods are allowed or restricted
- Macro distribution constraints
- How the nutrition engine behaves
- How automatic adjustments are applied

Diet types are treated as **configuration**, not hardcoded logic.

---

## Global Rules (All Diet Types)

The following rules apply to every diet type:

- Protein intake is always prioritized
- Users can freely edit meals
- The system automatically rebalances macros
- The nutrition engine enforces diet constraints
- The user is never required to calculate macros manually

---

## 1. Normal Diet

### Description
A flexible diet with no food restrictions, focused on macro balance.

### Allowed Foods
- All foods from the official food base
- All user-created custom foods

### Restricted Foods
- None

### Macro Guidelines
- Protein: based on user goal
- Fat: 20–30% of total calories
- Carbohydrates: remaining calories

### Engine Behavior
- Carbohydrates are adjusted first
- Fat is adjusted second
- Protein is preserved whenever possible

---

## 2. Vegetarian Diet

### Description
Excludes animal meats while maintaining nutritional adequacy.

### Allowed Foods
- Eggs
- Dairy products
- Legumes
- Grains
- Vegetables
- Fruits
- Nuts and seeds

### Restricted Foods
- Red meat
- Poultry
- Fish
- Seafood

### Macro Guidelines
- Protein target is increased to compensate for lower bioavailability
- Carbohydrates are allowed
- Fat remains moderate

### Engine Behavior
- Prioritizes eggs and dairy as protein sources
- Uses legumes as secondary protein sources
- Alerts the user if protein targets cannot be met

---

## 3. Vegan Diet

### Description
Excludes all animal-derived products.

### Allowed Foods
- Legumes
- Grains
- Vegetables
- Fruits
- Nuts and seeds
- Plant-based fats

### Restricted Foods
- Meat
- Fish
- Eggs
- Dairy
- Any animal-derived ingredient

### Macro Guidelines
- Higher protein target compared to normal diet
- Careful balance of carbohydrates and fats

### Engine Behavior
- Protein sources are plant-based only
- Engine may require higher food volume to meet protein targets
- Alerts are shown if nutritional limits are reached

---

## 4. Carnivore Diet

### Description
A diet based exclusively on animal products.

### Allowed Foods
- Red meat
- Poultry
- Fish
- Eggs
- Animal fats

### Restricted Foods
- All vegetables
- Fruits
- Grains
- Legumes
- Sugars

### Macro Guidelines
- Carbohydrates are near zero
- Protein intake is high
- Fat intake is flexible based on goal

### Engine Behavior
- No carbohydrate-based adjustments
- Adjustments are made by changing fat ratios and protein portions
- Micronutrient warnings may be displayed but do not block usage

---

## 5. Ketogenic Diet

### Description
A low-carbohydrate diet designed to maintain ketosis.

### Allowed Foods
- Meats
- Eggs
- Fish
- Healthy fats
- Low-carb vegetables

### Restricted Foods
- Sugars
- Grains
- High-carb fruits
- Tubers
- Legumes

### Macro Guidelines
- Carbohydrates: hard limit (≤ 50g/day)
- Protein: moderate
- Fat: primary calorie source

### Engine Behavior
- Carbohydrate limit is strictly enforced
- Adjustments are made using fat, not carbs
- Foods exceeding carb limits are blocked

---

## 6. Jungle Diet (Easy Diet Exclusive)

### Description
A diet focused on **natural, minimally processed foods**, prioritizing satiety, metabolic health, and performance.

This diet sits between a traditional and a ketogenic approach, without extremes.

### Allowed Foods
- Meats
- Eggs
- Fish
- Vegetables
- Fruits (whole)
- Tubers (potato, sweet potato, cassava, squash)
- Natural fats (olive oil, butter, coconut)

### Restricted Foods
- Refined sugars
- Flours
- Ultra-processed foods
- Industrial oils
- Sweetened beverages

### Macro Guidelines
- Protein: high
- Fat: moderate
- Carbohydrates: only from natural sources

### Engine Behavior
- Prefers reducing natural carbohydrates before reducing food volume
- Never replaces food with processed alternatives
- Adjustments preserve food quality and satiety

---

## Diet Type as a Domain Concept

Diet types are modeled as:
- A domain entity
- A database table in the future
- A configurable input to the nutrition engine

Diet rules are **not embedded** in the diet entity itself.

All behavioral rules live in the engine layer.

---

## Summary

Each diet type in Easy Diet defines **constraints**, not rigid plans.

The system ensures:
- Nutritional consistency
- User flexibility
- Automatic adaptation

The user chooses the diet.  
The system ensures it works.
