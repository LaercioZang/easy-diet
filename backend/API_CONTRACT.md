# Easy Diet Backend API Contract (Current State)

This document outlines the current REST API contract for the Easy Diet backend, focusing on the `Food`, `FoodCategory`, and `DietType` resources. This contract reflects the state as of the latest development cycle and includes the standardized error response format.

## 1. Standardized Error Response

All API errors are returned in a consistent JSON format, handled by the `GlobalExceptionHandler`.

### Error Response Structure

| Field | Type | Description |
| :--- | :--- | :--- |
| `timestamp` | `String` | The time the error occurred (ISO 8601). |
| `status` | `Integer` | The HTTP status code (e.g., 400, 404, 500). |
| `error` | `String` | The HTTP status reason phrase (e.g., "Bad Request", "Not Found"). |
| `code` | `String` | The internal application error code (e.g., `RESOURCE_NOT_FOUND`, `INVALID_ARGUMENT`). |
| `message` | `String` | A human-readable message detailing the error. |
| `fieldErrors` | `List` | (Optional) List of field-specific validation errors. |

### Example: Resource Not Found (404)

```json
{
  "timestamp": "2026-01-04T12:00:00.000Z",
  "status": 404,
  "error": "Not Found",
  "code": "RESOURCE_NOT_FOUND",
  "message": "The requested resource was not found."
}
```

### Example: Validation Error (400)

```json
{
  "timestamp": "2026-01-04T12:00:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "code": "INVALID_ARGUMENT",
  "message": "Validation failed for one or more fields",
  "fieldErrors": [
    {
      "field": "name",
      "message": "Diet name is required"
    }
  ]
}
```

## 2. Resource Endpoints

### 2.1. Food Categories (`/api/food-categories`)

| Method | Path | Description | Request Body | Response Body | Status Codes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/` | Retrieves all categories. Use `?active=true` to filter. | None | `List<FoodCategoryResponse>` | 200 |
| `GET` | `/{id}` | Retrieves a category by ID. | None | `FoodCategoryResponse` | 200, 404 |
| `POST` | `/` | Creates a new category. | `FoodCategoryRequest` | `FoodCategoryResponse` | 201, 400 |
| `PUT` | `/{id}` | Updates an existing category. | `FoodCategoryRequest` | `FoodCategoryResponse` | 200, 400, 404 |
| `DELETE` | `/{id}` | Deletes a category. | None | None | 204, 404 |

**`FoodCategoryResponse` DTO:** Includes `id`, `code`, `name`, and `active`.

### 2.2. Diet Types (`/api/diet-types`)

| Method | Path | Description | Request Body | Response Body | Status Codes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/` | Retrieves all diet types. Use `?active=true` to filter. | None | `List<DietTypeResponse>` | 200 |
| `GET` | `/{id}` | Retrieves a diet type by ID. | None | `DietTypeResponse` | 200, 404 |
| `POST` | `/` | Creates a new diet type. | `DietTypeRequest` | `DietTypeResponse` | 201, 400 |
| `PUT` | `/{id}` | Updates an existing diet type. | `DietTypeRequest` | `DietTypeResponse` | 200, 400, 404 |
| `DELETE` | `/{id}` | Deletes a diet type. | None | None | 204, 404 |

**`DietTypeResponse` DTO:** Includes `id`, `code`, `name`, and `active`.

### 2.3. Foods (`/api/foods`)

**Note:** This controller has an inconsistent filtering endpoint that uses a path variable instead of a query parameter. This is a known issue to be addressed in a future versioning effort.

| Method | Path | Description | Request Body | Response Body | Status Codes |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `GET` | `/` | Retrieves all active foods. | None | `List<FoodResponse>` | 200 |
| `GET` | `/category/{code}` | **(Inconsistent)** Retrieves active foods by category code. | None | `List<FoodResponse>` | 200, 400 |

**`FoodResponse` DTO:** Includes `id`, `name`, `calories`, and macronutrients (`protein`, `carbs`, `fat`), and a nested `FoodCategoryResponse`.

## 3. Exception Mapping

The `GlobalExceptionHandler` maps internal exceptions to HTTP status codes as follows:

| Internal Exception | ErrorCode | HTTP Status |
| :--- | :--- | :--- |
| `DomainException` (RESOURCE_NOT_FOUND) | `RESOURCE_NOT_FOUND` | 404 Not Found |
| `DomainException` (Validation Codes) | `INVALID_ARGUMENT`, `NULL_VALUE`, etc. | 400 Bad Request |
| `DomainException` (UNSUPPORTED_DIET) | `UNSUPPORTED_DIET` | 422 Unprocessable Entity |
| `MethodArgumentNotValidException` | `INVALID_ARGUMENT` | 400 Bad Request |
| `Exception` (Generic) | `INTERNAL_ERROR` | 500 Internal Server Error |
