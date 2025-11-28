# Jenkins Tool

A Spring Boot application for working with a database-backed service.

## Quick start
1. Create the database and user credentials the service should use.
2. Update `src/main/resources/application.yml` with the connection details (either replace the `${...}` placeholders or export the corresponding environment variables `DB_HOST`, `DB_NAME`, `DB_USER`, and `DB_PASSWORD`).
3. Build and run the app:
   - `./mvnw clean package`
   - `./mvnw spring-boot:run`

Ensure Java 17+ is available before running the commands.

4. After startup, open http://localhost:8080 in your browser to verify the service is running.
