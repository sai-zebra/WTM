# Setup Notes

## OpenAPI Generated Classes

The OpenAPI generated classes are located in:
```
src/main/java/org/openapitools/
```

### Important: Module Access

In a multi-module Maven project, these classes need to be accessible to all modules. You have two options:

#### Option 1: Copy to Application Module (Recommended for Demo)
Copy the `org.openapitools` package to:
```
wtm-api-application/src/main/java/org/openapitools/
```

#### Option 2: Create a Shared Module (Recommended for Production)
1. Create a new module `wtm-api-openapi`
2. Move the OpenAPI classes there
3. Add it as a dependency to all modules that need it

### Current Setup

For this demo, the OpenAPI classes are referenced from the root `src` directory. To make them accessible:

1. **Copy the classes** to the application module:
   ```bash
   # Windows PowerShell
   Copy-Item -Recurse src\main\java\org\openapitools wtm-api-application\src\main\java\org\
   ```

2. **Or manually copy** the `org.openapitools` folder from `src/main/java/` to `wtm-api-application/src/main/java/`

## Build Configuration

The parent POM (`pom.xml`) is configured as a multi-module project. All modules are listed in the `<modules>` section.

## Component Scanning

The main application class (`WtmApiApplication`) includes component scanning for:
- All module packages (`com.workcloud.wtm.*`)
- OpenAPI package (`org.openapitools`)

## Running the Application

After ensuring OpenAPI classes are accessible:

```bash
mvn clean install
cd wtm-api-application
mvn spring-boot:run
```

## Verification

Once running, verify:
1. Application starts without errors
2. Swagger UI is accessible: http://localhost:8080/mywork/v1/swagger-ui.html
3. API endpoints respond correctly
