# WTM API - Project Documentation

## Table of Contents
1. [Architecture Overview](#architecture-overview)
2. [Project Structure](#project-structure)
3. [Hexagonal Architecture](#hexagonal-architecture)
4. [Modular Monolith Architecture](#modular-monolith-architecture)
5. [Module Details](#module-details)
6. [REST API Documentation](#rest-api-documentation)
7. [How to Run](#how-to-run)
8. [Testing](#testing)
9. [Development Guidelines](#development-guidelines)

---

## Architecture Overview

This project implements a **Spring Boot application** using two key architectural patterns:

1. **Hexagonal Architecture (Ports & Adapters)**: Each module is structured with clear separation between business logic and external concerns
2. **Modular Monolith Architecture**: The application is split into multiple modules, each representing a bounded context, while still being deployed as a single application

### Key Benefits

- **Maintainability**: Clear separation of concerns makes the codebase easier to understand and modify
- **Testability**: Business logic is isolated from infrastructure, making unit testing straightforward
- **Flexibility**: Easy to swap implementations (e.g., in-memory repository → database repository)
- **Scalability**: Modules can be extracted into microservices if needed in the future
- **Team Collaboration**: Different teams can work on different modules independently

---

## Project Structure

```
WTM-API-main/
├── pom.xml                          # Parent POM for multi-module project
├── wtm-api-common/                  # Shared utilities and common code
├── wtm-api-feeds/                   # Feeds module (fully implemented)
│   ├── domain/                      # Domain layer (entities, value objects)
│   ├── application/                 # Application layer (use cases, ports)
│   ├── infrastructure/              # Infrastructure layer (adapters)
│   └── adapter/                     # Web adapters (REST controllers)
├── wtm-api-feednotes/               # Feed Notes module
├── wtm-api-surveys/                 # Surveys module
├── wtm-api-rtm/                     # Real-Time Management module
├── wtm-api-users/                   # Users module
├── wtm-api-sessions/                # Sessions module
└── wtm-api-application/             # Main Spring Boot application
    └── src/main/java/
        └── com/workcloud/wtm/
            └── WtmApiApplication.java
```

---

## Hexagonal Architecture

### Overview

Hexagonal Architecture (also known as Ports & Adapters) separates the application into distinct layers:

```
┌─────────────────────────────────────────┐
│         Adapters (Inbound)              │
│  ┌──────────┐  ┌──────────┐           │
│  │   REST   │  │   CLI    │  ...       │
│  └────┬─────┘  └────┬─────┘           │
└───────┼──────────────┼─────────────────┘
        │              │
┌───────▼──────────────▼─────────────────┐
│      Application Layer (Use Cases)       │
│  ┌──────────────────────────────┐      │
│  │   Input Ports (Interfaces)   │      │
│  └──────────────────────────────┘      │
│  ┌──────────────────────────────┐      │
│  │   Application Services       │      │
│  └──────────────────────────────┘      │
│  ┌──────────────────────────────┐      │
│  │   Output Ports (Interfaces) │      │
│  └──────────────────────────────┘      │
└───────┬────────────────────────────────┘
        │
┌───────▼─────────────────────────────────┐
│      Domain Layer                       │
│  ┌──────────────────────────────┐      │
│  │   Entities & Value Objects   │      │
│  │   Business Logic             │      │
│  └──────────────────────────────┘      │
└───────┬────────────────────────────────┘
        │
┌───────▼─────────────────────────────────┐
│      Adapters (Outbound)                │
│  ┌──────────┐  ┌──────────┐           │
│  │Database  │  │External  │  ...       │
│  │Repository│  │Service   │           │
│  └──────────┘  └──────────┘           │
└─────────────────────────────────────────┘
```

### Layer Responsibilities

#### 1. Domain Layer (`domain/`)
- **Purpose**: Contains core business logic and domain entities
- **Contains**:
  - Domain entities (e.g., `Feed`, `FeedStatus`)
  - Business rules and validation
  - Domain events (if needed)
- **Rules**: 
  - No dependencies on other layers
  - Pure Java objects (no framework dependencies)

#### 2. Application Layer (`application/`)
- **Purpose**: Orchestrates use cases and coordinates between domain and infrastructure
- **Contains**:
  - **Input Ports** (`port/in/`): Interfaces defining what the application can do (use cases)
  - **Output Ports** (`port/out/`): Interfaces defining what the application needs from outside
  - **Services**: Implementations of use cases
  - **DTOs**: Data Transfer Objects for layer communication
- **Rules**:
  - Depends only on domain layer
  - Defines contracts (ports) that adapters must implement

#### 3. Infrastructure Layer (`infrastructure/`)
- **Purpose**: Implements technical concerns (persistence, external services)
- **Contains**:
  - **Adapters**: Implementations of output ports
  - Repository implementations (e.g., `InMemoryFeedRepository`)
  - External service clients
- **Rules**:
  - Implements ports defined in application layer
  - Can use framework-specific code (Spring, JPA, etc.)

#### 4. Adapters Layer (`adapter/`)
- **Purpose**: Entry points for external interactions
- **Contains**:
  - **Web Adapters** (`adapter/in/web/`): REST controllers
  - CLI adapters (if needed)
  - Message queue listeners (if needed)
- **Rules**:
  - Translates external requests to use case calls
  - Implements OpenAPI-generated interfaces

### Example: Feeds Module Structure

```
wtm-api-feeds/
├── domain/
│   └── model/
│       ├── Feed.java              # Domain entity
│       └── FeedStatus.java       # Value object
├── application/
│   ├── port/
│   │   ├── in/                   # Input ports (use cases)
│   │   │   ├── CreateFeedUseCase.java
│   │   │   ├── GetFeedUseCase.java
│   │   │   └── ...
│   │   └── out/                  # Output ports
│   │       └── FeedRepositoryPort.java
│   ├── service/
│   │   └── FeedService.java      # Use case implementations
│   ├── dto/
│   │   └── FeedDto.java          # Data Transfer Object
│   └── mapper/
│       └── FeedMapper.java       # Entity ↔ DTO mapper
├── infrastructure/
│   └── adapter/
│       └── persistence/
│           └── InMemoryFeedRepository.java  # Repository implementation
└── adapter/
    └── in/
        └── web/
            └── FeedController.java          # REST controller
```

---

## Modular Monolith Architecture

### Overview

A Modular Monolith is a single deployable application composed of multiple modules, where each module represents a bounded context (domain). This approach provides:

- **Clear boundaries**: Each module has its own domain model
- **Independent development**: Teams can work on different modules
- **Future flexibility**: Modules can be extracted into microservices if needed

### Module Organization

Each module follows the same Hexagonal Architecture pattern:

1. **wtm-api-feeds**: Manages feeds (tasks/work items)
2. **wtm-api-feednotes**: Manages notes associated with feeds
3. **wtm-api-surveys**: Manages surveys and responses
4. **wtm-api-rtm**: Real-Time Management operations
5. **wtm-api-users**: User management
6. **wtm-api-sessions**: Session management (login/logout)

### Module Communication

- Modules communicate through **application services** (use cases)
- No direct database sharing between modules
- Each module maintains its own domain model
- Communication is explicit through defined interfaces

---

## Module Details

### Feeds Module (Fully Implemented)

The feeds module demonstrates the complete Hexagonal Architecture implementation.

#### Domain Model
- **Feed**: Core entity representing a work item/task
- **FeedStatus**: Enum representing feed states (PENDING, CLAIMED, ACKNOWLEDGED, COMPLETED, ESCALATED)

#### Use Cases
- `CreateFeedUseCase`: Create a new feed
- `GetFeedUseCase`: Retrieve a feed by ID
- `ListFeedsUseCase`: List feeds with pagination
- `UpdateFeedUseCase`: Update feed details
- `DeleteFeedUseCase`: Delete a feed
- `PerformFeedOperationUseCase`: Perform operations (claim, acknowledge, complete, etc.)

#### Repository
- `InMemoryFeedRepository`: In-memory implementation (can be replaced with JPA/database)

---

## REST API Documentation

### Base URL
```
http://localhost:8080/mywork/v1
```

### Swagger UI
```
http://localhost:8080/mywork/v1/swagger-ui.html
```

### API Endpoints

#### Feeds

##### Create Feed
```http
POST /feeds
Content-Type: application/json

{
  "title": "Task Title",
  "message": "Task description"
}
```

**Response (201 Created)**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Task Title",
  "message": "Task description",
  "status": "PENDING",
  "createdAt": "2024-01-20T10:30:00Z"
}
```

##### Get Feed
```http
GET /feeds/{feedId}
```

**Response (200 OK)**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Task Title",
  "message": "Task description",
  "status": "PENDING",
  "createdAt": "2024-01-20T10:30:00Z"
}
```

##### List Feeds
```http
GET /feeds?pageSize=10&pageToken=0
```

**Response (200 OK)**
```json
{
  "items": [
    {
      "id": "550e8400-e29b-41d4-a716-446655440000",
      "title": "Task Title",
      "message": "Task description",
      "status": "PENDING",
      "createdAt": "2024-01-20T10:30:00Z"
    }
  ]
}
```

##### Update Feed
```http
PATCH /feeds/{feedId}
Content-Type: application/json

{
  "title": "Updated Title",
  "message": "Updated message"
}
```

**Response (200 OK)**

##### Delete Feed
```http
DELETE /feeds/{feedId}
```

**Response (204 No Content)**

##### Perform Feed Operation
```http
POST /feeds/{feedId}/operations
Content-Type: application/json

{
  "operation": "CLAIM"
}
```

**Valid Operations:**
- `CLAIM`: Claim a pending feed
- `REASSIGN`: Reassign a feed back to pending
- `ACKNOWLEDGE`: Acknowledge a claimed feed
- `COMPLETE`: Complete a feed
- `ESCALATE`: Escalate a feed

**Response (202 Accepted)**

#### Feed Notes

##### Create Feed Note
```http
POST /feeds/{feedId}/notes
Content-Type: application/json

{
  "message": "Note content"
}
```

**Response (201 Created)**

##### List Feed Notes
```http
GET /feeds/{feedId}/notes
```

**Response (200 OK)**
```json
{
  "items": [
    {
      "id": "note-id",
      "message": "Note content",
      "createdAt": "2024-01-20T10:30:00Z"
    }
  ]
}
```

##### Update Feed Note
```http
PATCH /feeds/{feedId}/notes/{noteId}
Content-Type: application/json

{
  "message": "Updated note content"
}
```

**Response (200 OK)**

##### Delete Feed Note
```http
DELETE /feeds/{feedId}/notes/{noteId}
```

**Response (204 No Content)**

#### Surveys

##### Create Survey
```http
POST /surveys
Content-Type: application/json

{
  "title": "Survey Title",
  "questions": ["Question 1", "Question 2"]
}
```

**Response (201 Created)**

##### List Surveys
```http
GET /surveys
```

**Response (200 OK)**

##### Submit Survey Response
```http
POST /surveys/{surveyId}/responses
Content-Type: application/json

{
  "responses": {
    "question1": "answer1",
    "question2": "answer2"
  }
}
```

**Response (201 Created)**

#### RTM Operations

##### Perform RTM Operation
```http
POST /rtm/operations
Content-Type: application/json

{
  "operation": "SEND_SURVEY",
  "payload": {}
}
```

**Valid Operations:**
- `SEND_SURVEY`
- `BROADCAST`
- `NUDGE`
- `ESCALATE`
- `REASSIGN`

**Response (202 Accepted)**

#### Users

##### Get User
```http
GET /users/{userId}
```

**Response (200 OK)**

##### List Users
```http
GET /users
```

**Response (200 OK)**

##### Upload User Image
```http
PUT /users/{userId}/image
Content-Type: image/*

[binary image data]
```

**Response (200 OK)**

#### Sessions

##### Login Success
```http
POST /sessions/login
```

**Response (200 OK)**

##### Logout
```http
POST /sessions/logout
```

**Response (204 No Content)**

---

## How to Run

### Prerequisites

- **Java 11** or higher
- **Maven 3.6+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

### Build the Project

```bash
# Navigate to project root
cd WTM-API-main

# Build all modules
mvn clean install

# Skip tests (if needed)
mvn clean install -DskipTests
```

### Run the Application

#### Option 1: Using Maven
```bash
cd wtm-api-application
mvn spring-boot:run
```

#### Option 2: Using Java
```bash
cd wtm-api-application
java -jar target/wtm-api-application-1.1.0.jar
```

#### Option 3: Using IDE
1. Open the project in your IDE
2. Navigate to `wtm-api-application/src/main/java/com/workcloud/wtm/WtmApiApplication.java`
3. Run the `main` method

### Verify the Application

1. **Check Health**: The application should start on port 8080
2. **Access Swagger UI**: Open `http://localhost:8080/mywork/v1/swagger-ui.html`
3. **Test Endpoint**: 
   ```bash
   curl http://localhost:8080/mywork/v1/feeds
   ```

### Application Configuration

Configuration is in `wtm-api-application/src/main/resources/application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /mywork/v1

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

---

## Testing

### Run All Tests

```bash
mvn test
```

### Run Tests for Specific Module

```bash
cd wtm-api-feeds
mvn test
```

### Test Coverage

The feeds module includes comprehensive unit tests:

- **Domain Tests**: `FeedTest.java` - Tests domain entity behavior
- **Service Tests**: `FeedServiceTest.java` - Tests use case implementations

### Example Test Structure

```java
@ExtendWith(MockitoExtension.class)
class FeedServiceTest {
    @Mock
    private FeedRepositoryPort feedRepository;
    
    @InjectMocks
    private FeedService feedService;
    
    @Test
    void createFeed_ShouldReturnFeedDto() {
        // Given
        // When
        // Then
    }
}
```

---

## Development Guidelines

### Adding a New Module

1. **Create Module Structure**:
   ```
   wtm-api-{module}/
   ├── domain/
   ├── application/
   ├── infrastructure/
   └── adapter/
   ```

2. **Follow Hexagonal Architecture**:
   - Domain layer: Pure business logic
   - Application layer: Use cases and ports
   - Infrastructure layer: Adapter implementations
   - Adapter layer: REST controllers

3. **Update Parent POM**: Add module to parent `pom.xml`

4. **Update Application Module**: Add dependency in `wtm-api-application/pom.xml`

### Adding a New Use Case

1. **Define Input Port** (`application/port/in/`):
   ```java
   public interface CreateXxxUseCase {
       XxxDto createXxx(CreateXxxCommand command);
   }
   ```

2. **Implement in Service** (`application/service/`):
   ```java
   @Service
   public class XxxService implements CreateXxxUseCase {
       // Implementation
   }
   ```

3. **Create REST Endpoint** (`adapter/in/web/`):
   ```java
   @RestController
   public class XxxController {
       // Map HTTP to use case
   }
   ```

### Best Practices

1. **Domain Layer**:
   - Keep it pure (no framework dependencies)
   - Encapsulate business logic in entities
   - Use value objects for immutable concepts

2. **Application Layer**:
   - Keep use cases focused and single-purpose
   - Use DTOs for data transfer
   - Define clear port interfaces

3. **Infrastructure Layer**:
   - Implement ports, don't create new ones
   - Keep adapter code separate from business logic
   - Use dependency injection

4. **Testing**:
   - Test domain logic in isolation
   - Mock ports in service tests
   - Use integration tests for adapters

---

## Troubleshooting

### Build Issues

**Problem**: Module not found
**Solution**: Run `mvn clean install` from parent directory

**Problem**: Port already in use
**Solution**: Change port in `application.yml` or stop the process using port 8080

### Runtime Issues

**Problem**: Bean not found
**Solution**: Ensure `@ComponentScan` includes your module package

**Problem**: 404 on endpoints
**Solution**: Check context path is `/mywork/v1` and endpoint paths match

---

## Next Steps

1. **Implement Remaining Modules**: Complete implementations for feednotes, surveys, rtm, users, and sessions
2. **Add Database**: Replace in-memory repositories with JPA/database implementations
3. **Add Security**: Implement OAuth2 authentication
4. **Add Validation**: Enhance input validation
5. **Add Logging**: Implement structured logging
6. **Add Monitoring**: Add health checks and metrics

---

## Contact & Support

For questions or issues, please refer to the project repository or contact the development team.
