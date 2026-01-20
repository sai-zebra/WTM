# Running WTM API Application in IntelliJ IDEA

## Prerequisites
- IntelliJ IDEA (Community or Ultimate edition)
- Java 11 or higher installed
- Maven installed (or use IntelliJ's bundled Maven)

## Step-by-Step Setup

### 1. Open the Project in IntelliJ

1. **Open IntelliJ IDEA**
2. **File → Open** (or **File → New → Project from Existing Sources**)
3. Navigate to the project root: `WTM-API-main/WTM-API-main`
4. Select the folder and click **OK**
5. IntelliJ will detect it's a Maven project and import it automatically

### 2. Wait for Maven Import

- IntelliJ will automatically detect the `pom.xml` files
- Wait for the Maven import to complete (you'll see progress in the bottom right)
- If prompted, click **Import Maven Project** or **Enable Auto-Import**

### 3. Configure Project SDK

1. **File → Project Structure** (or press `Ctrl+Alt+Shift+S`)
2. Go to **Project** settings
3. Set **Project SDK** to **Java 11** or higher
4. Set **Project language level** to **11** or higher
5. Click **OK**

### 4. Build the Project

1. **Build → Build Project** (or press `Ctrl+F9`)
2. Wait for the build to complete
3. Check the **Build** tool window at the bottom for any errors

### 5. Run the Application

#### Option A: Run from Main Class (Recommended)

1. Navigate to the main class:
   - Open: `wtm-api-application/src/main/java/com/workcloud/wtm/WtmApiApplication.java`
2. Right-click on the class file or the `main` method
3. Select **Run 'WtmApiApplication.main()'**
   - Or press `Ctrl+Shift+F10` when the cursor is on the class
4. The application will start and you'll see logs in the **Run** tool window

#### Option B: Create Run Configuration

1. **Run → Edit Configurations...**
2. Click the **+** button and select **Spring Boot**
3. Configure:
   - **Name**: `WTM API Application`
   - **Main class**: `com.workcloud.wtm.WtmApiApplication`
   - **Module**: `wtm-api-application`
   - **Working directory**: `$MODULE_DIR$`
   - **Use classpath of module**: `wtm-api-application`
4. Click **OK**
5. Click the **Run** button (green play icon) or press `Shift+F10`

### 6. Verify the Application is Running

1. Check the **Run** tool window for:
   ```
   Started WtmApiApplication in X.XXX seconds
   ```
2. Look for the port information:
   ```
   Tomcat started on port(s): 8080 (http)
   ```

### 7. Access the Application

Once running, you can access:

- **Swagger UI**: http://localhost:8080/mywork/v1/swagger-ui.html
- **API Docs**: http://localhost:8080/mywork/v1/api-docs
- **API Base**: http://localhost:8080/mywork/v1

## Troubleshooting

### Issue: "Cannot resolve symbol" errors

**Solution:**
1. **File → Invalidate Caches / Restart...**
2. Select **Invalidate and Restart**
3. Wait for IntelliJ to re-index

### Issue: Maven dependencies not downloading

**Solution:**
1. **View → Tool Windows → Maven**
2. Click the **Reload All Maven Projects** icon (circular arrow)
3. Or right-click on the root `pom.xml` → **Maven → Reload Project**

### Issue: "Module not found" errors

**Solution:**
1. **File → Project Structure** (`Ctrl+Alt+Shift+S`)
2. Go to **Modules**
3. Ensure all modules are listed:
   - wtm-api-common
   - wtm-api-openapi
   - wtm-api-feeds
   - wtm-api-feednotes
   - wtm-api-surveys
   - wtm-api-rtm
   - wtm-api-users
   - wtm-api-sessions
   - wtm-api-application
4. If any are missing, click **+** → **Import Module** and select the module's `pom.xml`

### Issue: Port 8080 already in use

**Solution:**
1. Stop any other application using port 8080
2. Or change the port in: `wtm-api-application/src/main/resources/application.yml`
   ```yaml
   server:
     port: 8081  # Change to another port
   ```

### Issue: "Main class not found"

**Solution:**
1. Ensure you're running from `wtm-api-application` module
2. The main class should be: `com.workcloud.wtm.WtmApiApplication`
3. Check that the module is properly built: **Build → Build Project**

## Debugging

### Run in Debug Mode

1. Set breakpoints in your code (click in the left margin)
2. Right-click on `WtmApiApplication.java` → **Debug 'WtmApiApplication.main()'**
   - Or press `Shift+F9`
3. The application will pause at breakpoints
4. Use the **Debug** tool window to inspect variables and step through code

## Useful IntelliJ Shortcuts

- **Run**: `Shift+F10`
- **Debug**: `Shift+F9`
- **Build Project**: `Ctrl+F9`
- **Project Structure**: `Ctrl+Alt+Shift+S`
- **Maven Tool Window**: `Alt+4` (or **View → Tool Windows → Maven**)
- **Run Tool Window**: `Alt+4`
- **Terminal**: `Alt+F12`

## Running Tests

### Run All Tests

1. Right-click on `src/test` folder in any module
2. Select **Run 'All Tests'**

### Run Specific Test

1. Open a test file (e.g., `FeedServiceTest.java`)
2. Right-click on the test class or method
3. Select **Run 'FeedServiceTest'** or **Run 'testMethodName()'**
4. Or press `Ctrl+Shift+F10`

## Maven Integration

IntelliJ has built-in Maven support. You can also run Maven commands:

1. Open **Maven** tool window (`Alt+4`)
2. Expand the project → **Lifecycle**
3. Double-click on:
   - **clean**: Clean build artifacts
   - **compile**: Compile source code
   - **test**: Run tests
   - **package**: Build JAR files
   - **install**: Install to local repository

## Recommended IntelliJ Settings

1. **File → Settings** (`Ctrl+Alt+S`)
2. **Build, Execution, Deployment → Build Tools → Maven**
   - Enable **Use plugin registry**
   - Set **Maven home directory** (or use bundled)
3. **Editor → Code Style → Java**
   - Import code style if your team has one
4. **Editor → Inspections**
   - Ensure Java inspections are enabled

## Quick Start Checklist

- [ ] Project opened in IntelliJ
- [ ] Maven import completed
- [ ] Java SDK set to 11+
- [ ] Project built successfully (`Ctrl+F9`)
- [ ] Main class found: `WtmApiApplication.java`
- [ ] Application runs without errors
- [ ] Swagger UI accessible at http://localhost:8080/mywork/v1/swagger-ui.html

## Additional Tips

1. **Enable Auto-Import**: When IntelliJ detects missing imports, click the lightbulb and select **Add import** or enable auto-import
2. **Code Completion**: IntelliJ will provide code completion for Spring Boot annotations and classes
3. **Spring Boot Support**: IntelliJ Ultimate has enhanced Spring Boot support, but Community edition works fine too
4. **Live Templates**: Use `psvm` + Tab for `public static void main`, `sout` + Tab for `System.out.println()`

## Need Help?

If you encounter issues:
1. Check the **Event Log** (`Alt+0`) for IntelliJ notifications
2. Check the **Build** tool window for compilation errors
3. Check the **Run** tool window for runtime errors
4. Verify all modules are properly imported in **Project Structure**
