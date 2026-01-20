# Quick Start Guide

## Prerequisites
- Java 11+
- Maven 3.6+

## Build & Run

### Step 1: Build the Project
```bash
cd WTM-API-main
mvn clean install
```

### Step 2: Run the Application
```bash
cd wtm-api-application
mvn spring-boot:run
```

### Step 3: Verify
Open your browser and navigate to:
- Swagger UI: http://localhost:8080/mywork/v1/swagger-ui.html
- API Base: http://localhost:8080/mywork/v1

## Test the API

### Create a Feed
```bash
curl -X POST http://localhost:8080/mywork/v1/feeds \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Sample Task",
    "message": "This is a sample task description"
  }'
```

### List Feeds
```bash
curl http://localhost:8080/mywork/v1/feeds
```

### Get a Feed
```bash
curl http://localhost:8080/mywork/v1/feeds/{feedId}
```

### Perform Feed Operation (Claim)
```bash
curl -X POST http://localhost:8080/mywork/v1/feeds/{feedId}/operations \
  -H "Content-Type: application/json" \
  -d '{
    "operation": "CLAIM"
  }'
```

## Project Structure

```
WTM-API-main/
├── pom.xml                    # Parent POM
├── wtm-api-common/           # Shared code
├── wtm-api-feeds/            # Feeds module (fully implemented)
├── wtm-api-feednotes/        # Feed Notes module
├── wtm-api-surveys/          # Surveys module
├── wtm-api-rtm/              # RTM module
├── wtm-api-users/            # Users module
├── wtm-api-sessions/         # Sessions module
└── wtm-api-application/      # Main application
```

## Architecture

- **Hexagonal Architecture**: Each module follows Ports & Adapters pattern
- **Modular Monolith**: Multiple modules, single deployment

## Module Layers

Each module (e.g., `wtm-api-feeds`) contains:

```
domain/          # Business entities and logic
application/     # Use cases and ports
infrastructure/  # Repository implementations
adapter/         # REST controllers
```

## Running Tests

```bash
# All tests
mvn test

# Specific module
cd wtm-api-feeds
mvn test
```

## Troubleshooting

**Port 8080 already in use?**
- Change port in `wtm-api-application/src/main/resources/application.yml`

**Module not found?**
- Run `mvn clean install` from the root directory

**Bean not found?**
- Check that `@ComponentScan` in `WtmApiApplication` includes your module package

## Next Steps

1. Review [PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md) for detailed architecture
2. Explore the feeds module implementation as a reference
3. Implement remaining modules following the same pattern
