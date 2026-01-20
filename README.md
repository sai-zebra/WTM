# WTM API - Work Task Management API

A Spring Boot application implementing Hexagonal Architecture and Modular Monolith patterns.

## Quick Start

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Build and Run

```bash
# Build all modules
mvn clean install

# Run the application
cd wtm-api-application
mvn spring-boot:run
```

### Access the API

- **Base URL**: `http://localhost:8080/mywork/v1`
- **Swagger UI**: `http://localhost:8080/mywork/v1/swagger-ui.html`
- **API Docs**: `http://localhost:8080/mywork/v1/api-docs`

### Example API Call

```bash
# Create a feed
curl -X POST http://localhost:8080/mywork/v1/feeds \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My Task",
    "message": "Task description"
  }'

# List feeds
curl http://localhost:8080/mywork/v1/feeds
```

## Architecture

This project uses:
- **Hexagonal Architecture (Ports & Adapters)**: Clear separation between business logic and infrastructure
- **Modular Monolith**: Multiple modules organized by domain, deployed as a single application

## Project Structure

```
wtm-api/
├── wtm-api-common/          # Shared utilities
├── wtm-api-feeds/           # Feeds module (fully implemented)
├── wtm-api-feednotes/       # Feed Notes module
├── wtm-api-surveys/         # Surveys module
├── wtm-api-rtm/             # Real-Time Management module
├── wtm-api-users/           # Users module
├── wtm-api-sessions/        # Sessions module
└── wtm-api-application/     # Main Spring Boot application
```

## Documentation

For detailed documentation, see [PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md)

## Testing

```bash
# Run all tests
mvn test

# Run tests for a specific module
cd wtm-api-feeds
mvn test
```

## Development

See [PROJECT_DOCUMENTATION.md](./PROJECT_DOCUMENTATION.md) for:
- Architecture details
- Development guidelines
- API documentation
- Troubleshooting
