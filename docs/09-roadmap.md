# Easy Diet — Project Roadmap

## Purpose of This Document

This roadmap defines the **execution plan for the Easy Diet project**, prioritizing what must be built first and what should be intentionally postponed.

The roadmap is ordered by **criticality**, not by convenience.

---

## Guiding Principle

> If the nutrition engine is correct, everything else becomes easy.  
> If the engine is wrong, no UI or infrastructure will save the product.

---

## Phase 0 — Definition & Alignment (Completed)

### Goals
- Align product vision
- Define MVP scope
- Establish technical architecture
- Lock key decisions

### Status
✅ Completed

### Deliverables
- Product vision
- MVP definition
- Diet types and rules
- Nutrition engine logic
- Weekly planning logic
- UX flow
- Data model (ERD)
- API contracts

---

## Phase 1 — Core Backend Engines (Critical)

### Objective
Build and validate the **core intelligence** of Easy Diet.

### Tasks
- Implement Weekly Planning Engine
  - Create week structure
  - Training vs rest day logic
  - Calorie redistribution
  - Meal structure generation
- Implement Nutrition Engine
  - Calorie calculation
  - Macro distribution
  - Diet-specific constraints
- Implement Adjustment Engine
  - Automatic rebalance after edits
  - Priority-based macro adjustment
- Write unit tests for all engines
  - No Spring
  - No database
  - Deterministic behavior

### Rules
- Pure Java
- No persistence
- No framework coupling

---

## Phase 2 — Backend Orchestration (High Priority)

### Objective
Connect engines into coherent use cases.

### Tasks
- Finalize service layer
  - OnboardingService
  - WeekPlanService
  - CheckInService
- Define internal contracts between services and engines
- Normalize business rule exceptions
- Ensure clean boundaries between layers

---

## Phase 3 — API Layer (High Priority)

### Objective
Expose the backend capabilities safely to the mobile app.

### Tasks
- Implement minimal REST controllers
  - Health check
  - Onboarding
  - Weekly plan generation
  - Current plan retrieval
- Implement request/response DTOs
- Apply validation rules
- Standardize API error responses

### Notes
- No authentication in MVP
- API versioning required (`/api/v1`)

---

## Phase 4 — Persistence Layer (Medium Priority)

### Objective
Persist plans and history safely.

### Tasks
- Model PostgreSQL schema
- Implement JPA entities
- Configure migrations (Flyway or Liquibase)
- Seed reference data
  - Diet types
  - Food base (TACO)
- Implement repositories

### Rules
- Historical data is immutable
- New versions instead of updates

---

## Phase 5 — Security & Cloud (Medium Priority)

### Objective
Prepare the application for production deployment.

### Tasks
- Integrate OAuth2 authentication
- Use JWT tokens
- Configure AWS environment
- Separate dev and prod environments
- Configure logs and monitoring

### Notes
- Security is required before store release
- Not required for early development

---

## Phase 6 — Mobile Application (Lower Priority for Now)

### Objective
Build the mobile experience on top of a stable backend.

### Tasks
- Initialize React Native project
- Implement onboarding flow
- Implement weekly and daily views
- Implement meal editing
- Display automatic adjustment feedback
- Connect to backend API

### Platforms
- iOS (Apple App Store)
- Android (Google Play Store)

---

## Phase 7 — Post-MVP Enhancements (Future)

### Examples
- Advanced analytics
- Feature flags
- AI-assisted onboarding
- Web dashboard
- Premium plans
- Performance optimization

These features are intentionally excluded from the MVP.

---

## What Not to Do Early

- Do not build UI before engines are stable
- Do not add social features
- Do not add AI prematurely
- Do not over-optimize infrastructure
- Do not couple business rules to UI or persistence

---

## Success Criteria

The project is considered on track when:

- Engines behave predictably under tests
- Users can freely edit meals without breaking plans
- Weekly plans adapt correctly to changes
- Backend remains easy to reason about and extend

---

## Final Notes

This roadmap is a **living document**.

It should be updated only when:
- A phase is completed
- A decision is formally changed
- New validated requirements emerge

Clarity and discipline are more important than speed.
