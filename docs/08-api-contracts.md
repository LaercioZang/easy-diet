# Easy Diet — API Contracts (MVP)

## Purpose of This Document

This document defines the **API contracts for the Easy Diet MVP**.

It specifies:
- Available endpoints
- Request and response structures
- Error handling conventions
- Versioning strategy

These contracts are designed to support a **mobile-first application** and to evolve safely over time.

---

## General API Principles

- All APIs are versioned
- Backend owns all business rules
- Frontend sends intentions, not calculations
- Responses are explicit and deterministic
- Errors are user-friendly and non-technical

---

## Base Configuration

- Base URL: `/api/v1`
- Format: JSON
- Authentication: **Not required in MVP**
- Authentication in future: OAuth2 (JWT)

---

## Standard Response Format

All responses follow this structure:

```json
{
  "success": true,
  "data": {},
  "message": null
}
In case of errors:
{
  "success": false,
  "data": null,
  "message": "Human-readable explanation"
}

1. Health Check
GET /api/v1/health

Used to verify backend availability.

Response
1. Health Check
GET /api/v1/health

Used to verify backend availability.

Response
{
  "success": true,
  "data": {
    "status": "UP"
  }
}

2. Onboarding
POST /api/v1/onboarding

Creates the initial user profile and nutrition target.

Request
{
  "sex": "MALE",
  "age": 24,
  "heightCm": 178,
  "weightKg": 66,
  "goal": "BULK",
  "dietType": "SELVA",
  "mealsPerDay": 4,
  "trainingDays": ["MONDAY", "TUESDAY", "THURSDAY", "FRIDAY", "SATURDAY"]
}

Response
{
  "success": true,
  "data": {
    "userId": "uuid",
    "nutritionTarget": {
      "calories": 2600,
      "proteinGrams": 180,
      "carbsGrams": 320,
      "fatGrams": 70
    }
  }
}

3. Generate Weekly Plan
POST /api/v1/week-plans

Generates a new weekly plan based on the active configuration.

Request
{
  "userId": "uuid"
}

Response
{
  "success": true,
  "data": {
    "weekPlanId": "uuid",
    "startDate": "2026-01-06",
    "endDate": "2026-01-12"
  }
}

4. Get Current Week Plan
GET /api/v1/week-plans/current?userId=uuid

Returns the active weekly plan.

Response (simplified)
{
  "success": true,
  "data": {
    "dietType": "SELVA",
    "days": [
      {
        "dayOfWeek": "MONDAY",
        "trainingDay": true,
        "caloriesTarget": 2700,
        "meals": [
          {
            "name": "Breakfast",
            "foods": [
              {
                "foodName": "Egg",
                "quantityGrams": 200
              }
            ]
          }
        ]
      }
    ]
  }
}

5. Edit Meal — Replace Food
PUT /api/v1/meals/{mealId}/replace-food

Replaces one food with another in a meal.

Request
{
  "oldFoodId": "food-id",
  "newFoodId": "food-id"
}

Response
{
  "success": true,
  "message": "Meal updated and plan adjusted automatically"
}

6. Edit Meal — Update Quantity
PUT /api/v1/meal-foods/{mealFoodId}

Updates the quantity of a food within a meal.

Request
{
  "quantityGrams": 150
}

Response
{
  "success": true,
  "message": "Quantity updated and plan adjusted automatically"
}

7. Food Base
GET /api/v1/foods

Returns the available foods filtered by diet type.

Query Parameters

dietType

source (TACO | CUSTOM)

Response
{
  "success": true,
  "data": [
    {
      "id": "food-id",
      "name": "Egg",
      "calories": 155,
      "proteinGrams": 13,
      "carbsGrams": 1,
      "fatGrams": 11
    }
  ]
}

8. Create Custom Food
POST /api/v1/foods/custom

Creates a user-defined food.

Request
{
  "name": "Custom Protein",
  "calories": 120,
  "proteinGrams": 25,
  "carbsGrams": 2,
  "fatGrams": 1
}

Response
{
  "success": true,
  "data": {
    "foodId": "food-id"
  }
}

9. Weekly Check-in
POST /api/v1/check-ins

Registers a weekly weight check-in.

Request
{
  "userId": "uuid",
  "weightKg": 65.2
}

Response
{
  "success": true,
  "message": "Progress evaluated and plan adjusted if necessary"
}

Error Handling
Business Rule Violation

Returned when a user action violates diet constraints.

{
  "success": false,
  "message": "This food is not compatible with your selected diet type"
}

Validation Error

Returned when request data is invalid.

{
  "success": false,
  "message": "Invalid input data"
}

API Versioning Strategy

All endpoints are prefixed with /api/v1

Breaking changes result in a new version (/api/v2)

Older versions remain supported for active mobile apps

Summary

These API contracts define a clear boundary between:

Mobile application responsibilities

Backend business logic

Nutrition and planning engines

They ensure:

Flexibility

Safety

Predictable evolution

The backend remains the single source of truth.