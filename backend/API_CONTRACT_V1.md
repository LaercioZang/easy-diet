# Easy Diet Backend API Contract - Version 1 (v1)

This document defines the standardized API contract for version 1 of the Easy Diet backend. All endpoints will be prefixed with `/api/v1`. The primary goal of v1 is to enforce RESTful conventions, particularly by standardizing filtering via query parameters.

## 1. Base URL and Standardization

| Feature | v0 (Current) | v1 (Proposed) |
| :--- | :--- | :--- |
| **Base Path** | `/api/` | `/api/v1/` |
| **Filtering** | Inconsistent (Path variables and Query params) | **Standardized to Query Parameters** |
| **Error Handling** | Inconsistent (Pre-GlobalExceptionHandler) | **Standardized** (Post-GlobalExceptionHandler) |

## 2. Standardized Query Parameters for Filtering

All collection endpoints (`GET /api/v1/{resource}`) will support the following query parameters:

| Parameter | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `active` | `boolean` | `false` | If `true`, returns only resources marked as active. |
| `search` | `string` | None | Performs a case-insensitive search on the resource's primary name field. |
| `category` | `string` | None | Filters foods by the specified category code (e.g., `PROTEIN`). (Foods only) |

## 3. Resource Endpoints (v1)

### 3.1. Food Categories (`/api/v1/food-categories`)

| Method | Path | Description | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Retrieves all categories. Supports `?active=true` and `?search={term}`. | 200 |
| `GET` | `/{id}` | Retrieves a category by ID. | 200, 404 |
| `POST` | `/` | Creates a new category. | 201, 400 |
| `PUT` | `/{id}` | Updates an existing category. | 200, 400, 404 |
| `DELETE` | `/{id}` | Deletes a category. | 204, 404 |

### 3.2. Diet Types (`/api/v1/diet-types`)

| Method | Path | Description | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Retrieves all diet types. Supports `?active=true` and `?search={term}`. | 200 |
| `GET` | `/{id}` | Retrieves a diet type by ID. | 200, 404 |
| `POST` | `/` | Creates a new diet type. | 201, 400 |
| `PUT` | `/{id}` | Updates an existing diet type. | 200, 400, 404 |
| `DELETE` | `/{id}` | Deletes a diet type. | 204, 404 |

### 3.3. Foods (`/api/v1/foods`)

| Method | Path | Description | Status Codes |
| :--- | :--- | :--- | :--- |
| `GET` | `/` | Retrieves all foods. Supports `?active=true`, `?search={term}`, and `?category={code}`. | 200 |
| `GET` | `/{id}` | Retrieves a food item by ID. | 200, 404 |
| `POST` | `/` | Creates a new food item. | 201, 400 |
| `PUT` | `/{id}` | Updates an existing food item. | 200, 400, 404 |
| `DELETE` | `/{id}` | Deletes a food item. | 204, 404 |

## 4. Comparison and Migration Notes

The transition to v1 requires minimal changes for `FoodCategory` and `DietType` but a significant change for `Food`.

### 4.1. Endpoint Comparison (v0 vs. v1)

| Resource | v0 Endpoint (Current) | v1 Endpoint (Proposed) | Change Type |
| :--- | :--- | :--- | :--- |
| **Base** | `/api/{resource}` | `/api/v1/{resource}` | **Prefix Change** |
| **Food Filter** | `GET /api/foods/category/{code}` | `GET /api/v1/foods?category={code}` | **Breaking Change** |
| **Food List** | `GET /api/foods` (Returns only active) | `GET /api/v1/foods` (Returns all, use `?active=true` for filter) | **Behavioral Change** |

### 4.2. Migration Notes for Consumers

1.  **Update Base Path:** All consumers must update their base URL from `/api/` to `/api/v1/`.
2.  **Food Filtering:** The old path-based food filtering endpoint is **deprecated** and will be removed. Consumers must switch to the query parameter approach:
    *   **Old:** `GET /api/foods/category/PROTEIN`
    *   **New:** `GET /api/v1/foods?category=PROTEIN`
3.  **Food List Behavior:** The default behavior of `GET /api/v1/foods` will return all foods (active and inactive). To maintain the old behavior, consumers must explicitly use the query parameter: `GET /api/v1/foods?active=true`.
4.  **Error Handling:** The error response format is now standardized across all endpoints. Consumers should update their error parsing logic to expect the `ErrorResponse` DTO structure.