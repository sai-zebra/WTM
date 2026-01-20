# Architecture Diagrams

## System Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    WTM API Application                      │
│                  (Modular Monolith)                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │   Feeds      │  │  FeedNotes   │  │   Surveys     │    │
│  │   Module     │  │   Module     │  │   Module     │    │
│  └──────────────┘  └──────────────┘  └──────────────┘    │
│                                                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │     RTM      │  │    Users     │  │   Sessions   │    │
│  │   Module     │  │   Module     │  │   Module     │    │
│  └──────────────┘  └──────────────┘  └──────────────┘    │
│                                                             │
└─────────────────────────────────────────────────────────────┘
                          │
                          ▼
                    REST API Endpoints
```

## Hexagonal Architecture (Per Module)

```
┌─────────────────────────────────────────────────────────────┐
│                    Module (e.g., Feeds)                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────────────────────────────────────────────────┐  │
│  │           Adapters (Inbound)                         │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  FeedController (REST)                      │   │  │
│  │  │  - Implements FeedsApi interface            │   │  │
│  │  │  - Translates HTTP → Use Cases              │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  └─────────────────────────────────────────────────────┘  │
│                          │                                  │
│                          ▼                                  │
│  ┌─────────────────────────────────────────────────────┐  │
│  │        Application Layer (Use Cases)                 │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  Input Ports (Interfaces)                   │   │  │
│  │  │  - CreateFeedUseCase                        │   │  │
│  │  │  - GetFeedUseCase                           │   │  │
│  │  │  - ListFeedsUseCase                        │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  FeedService (Implementation)                │   │  │
│  │  │  - Orchestrates use cases                   │   │  │
│  │  │  - Coordinates domain & infrastructure     │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  Output Ports (Interfaces)                 │   │  │
│  │  │  - FeedRepositoryPort                       │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  └─────────────────────────────────────────────────────┘  │
│                          │                                  │
│                          ▼                                  │
│  ┌─────────────────────────────────────────────────────┐  │
│  │           Domain Layer                              │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  Feed (Entity)                               │   │  │
│  │  │  - Business logic                           │   │  │
│  │  │  - State management                         │   │  │
│  │  │  - Validation                               │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  FeedStatus (Value Object)                    │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  └─────────────────────────────────────────────────────┘  │
│                          │                                  │
│                          ▼                                  │
│  ┌─────────────────────────────────────────────────────┐  │
│  │      Infrastructure (Outbound Adapters)              │  │
│  │  ┌──────────────────────────────────────────────┐   │  │
│  │  │  InMemoryFeedRepository                      │   │  │
│  │  │  - Implements FeedRepositoryPort             │   │  │
│  │  │  - Can be replaced with JPA/Database         │   │  │
│  │  └──────────────────────────────────────────────┘   │  │
│  └─────────────────────────────────────────────────────┘  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow Example: Create Feed

```
1. HTTP Request
   POST /feeds
   {
     "title": "Task",
     "message": "Description"
   }
           │
           ▼
2. FeedController (Adapter)
   - Receives HTTP request
   - Validates input
   - Creates CreateFeedCommand
           │
           ▼
3. FeedService (Application)
   - Receives command
   - Creates domain entity (Feed.create())
   - Calls repository port
           │
           ▼
4. Feed Entity (Domain)
   - Business logic
   - Validation
   - State initialization
           │
           ▼
5. InMemoryFeedRepository (Infrastructure)
   - Saves entity
   - Returns saved entity
           │
           ▼
6. FeedService
   - Maps entity to DTO
   - Returns DTO
           │
           ▼
7. FeedController
   - Maps DTO to API model
   - Returns HTTP response
           │
           ▼
8. HTTP Response
   201 Created
   {
     "id": "...",
     "title": "Task",
     ...
   }
```

## Module Dependencies

```
wtm-api-application
    │
    ├──► wtm-api-feeds
    │       └──► wtm-api-common
    │
    ├──► wtm-api-feednotes
    │       ├──► wtm-api-feeds
    │       └──► wtm-api-common
    │
    ├──► wtm-api-surveys
    │       └──► wtm-api-common
    │
    ├──► wtm-api-rtm
    │       └──► wtm-api-common
    │
    ├──► wtm-api-users
    │       └──► wtm-api-common
    │
    └──► wtm-api-sessions
            ├──► wtm-api-users
            └──► wtm-api-common
```

## Layer Responsibilities

| Layer | Responsibility | Dependencies |
|-------|---------------|--------------|
| **Adapter (In)** | HTTP handling, request/response mapping | Application Layer |
| **Application** | Use case orchestration, business workflow | Domain Layer |
| **Domain** | Business logic, entities, value objects | None |
| **Infrastructure** | Technical implementations (DB, external services) | Application Layer (ports) |

## Key Principles

1. **Dependency Rule**: Dependencies point inward
   - Adapters → Application → Domain
   - Infrastructure → Application (via ports)

2. **Ports & Adapters**: 
   - Ports = Interfaces (contracts)
   - Adapters = Implementations

3. **Domain Independence**: 
   - Domain layer has no external dependencies
   - Pure business logic

4. **Testability**: 
   - Easy to mock ports
   - Domain logic testable in isolation
