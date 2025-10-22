# Formula 1 API - 2025 Season

A RESTful API for managing Formula 1 drivers and teams information for the 2025 season, built with Java 25 and Spring Boot 3.5.6 using API-first development approach.

## Features

- **API-First Design**: APIs generated from OpenAPI 3.0 specification
- **CRUD Operations**: Complete Create, Read, Update, Delete operations for drivers and teams
- **RESTful Best Practices**: Proper HTTP methods, status codes, and versioned endpoints
- **Error Handling**: RFC 7807 ProblemDetail for consistent error responses
- **Database**: PostgreSQL 18 with Flyway migrations
- **Testing**: Integration tests with Testcontainers
- **Documentation**: Interactive Swagger UI for API exploration

## Technology Stack

- **Java**: 25
- **Spring Boot**: 3.5.6
- **Database**: PostgreSQL 18 (Docker Alpine image)
- **API Specification**: OpenAPI 3.0
- **Build Tool**: Maven
- **Database Migration**: Flyway
- **Testing**: JUnit 5, Testcontainers, REST Assured
- **Documentation**: SpringDoc OpenAPI (Swagger UI)

## Prerequisites

- Java 25 or higher
- Docker and Docker Compose
- Maven 3.9+ (or use the Maven wrapper included)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd claude-code-mobile-experiment
```

### 2. Start PostgreSQL Database

```bash
docker-compose up -d
```

This will start a PostgreSQL 18 Alpine container with:
- Database: `f1db`
- Username: `f1user`
- Password: `f1pass`
- Port: `5432`

### 3. Build the Project

```bash
./mvnw clean install
```

This will:
- Generate API interfaces and models from OpenAPI specification
- Compile the application
- Run all tests (including integration tests with Testcontainers)

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The API will be available at: `http://localhost:8080`

## API Documentation

### Swagger UI

Once the application is running, access the interactive API documentation at:

```
http://localhost:8080/swagger-ui.html
```

### OpenAPI Specification

The raw OpenAPI specification is available at:

```
http://localhost:8080/api-docs
```

## API Endpoints

### Teams

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/teams` | Get all teams |
| GET | `/api/v1/teams/{teamId}` | Get team by ID |
| POST | `/api/v1/teams` | Create a new team |
| PUT | `/api/v1/teams/{teamId}` | Update a team |
| DELETE | `/api/v1/teams/{teamId}` | Delete a team |
| GET | `/api/v1/teams/{teamId}/drivers` | Get drivers for a team |

### Drivers

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/drivers` | Get all drivers |
| GET | `/api/v1/drivers?teamId={id}` | Get drivers filtered by team |
| GET | `/api/v1/drivers/{driverId}` | Get driver by ID |
| POST | `/api/v1/drivers` | Create a new driver |
| PUT | `/api/v1/drivers/{driverId}` | Update a driver |
| DELETE | `/api/v1/drivers/{driverId}` | Delete a driver |

## Example API Requests

### Get All Teams

```bash
curl -X GET http://localhost:8080/api/v1/teams
```

### Get Team by ID

```bash
curl -X GET http://localhost:8080/api/v1/teams/1
```

### Create a New Team

```bash
curl -X POST http://localhost:8080/api/v1/teams \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Audi F1 Team",
    "base": "Neuburg, Germany",
    "teamChief": "Andreas Seidl",
    "powerUnit": "Audi",
    "firstEntry": 2026,
    "championships": 0
  }'
```

### Create a New Driver

```bash
curl -X POST http://localhost:8080/api/v1/drivers \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Max",
    "lastName": "Verstappen",
    "driverNumber": 33,
    "nationality": "Dutch",
    "dateOfBirth": "1997-09-30",
    "teamId": 1
  }'
```

### Update a Driver

```bash
curl -X PUT http://localhost:8080/api/v1/drivers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Max",
    "lastName": "Verstappen",
    "driverNumber": 1,
    "nationality": "Dutch",
    "dateOfBirth": "1997-09-30",
    "teamId": 2
  }'
```

### Delete a Driver

```bash
curl -X DELETE http://localhost:8080/api/v1/drivers/1
```

## Error Handling

The API uses RFC 7807 ProblemDetail for error responses. All errors return a standardized JSON structure:

```json
{
  "type": "https://api.f1.com/problems/not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Driver not found with id: '999'",
  "instance": "/api/v1/drivers/999",
  "timestamp": "2025-10-22T10:30:00Z"
}
```

### Error Types

- **400 Bad Request**: Invalid input or validation errors
- **404 Not Found**: Resource not found
- **409 Conflict**: Resource already exists (duplicate team name or driver number)
- **500 Internal Server Error**: Unexpected server error

## Database Schema

### Teams Table

```sql
CREATE TABLE teams (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    base VARCHAR(100) NOT NULL,
    team_chief VARCHAR(100) NOT NULL,
    power_unit VARCHAR(50) NOT NULL,
    first_entry INTEGER,
    championships INTEGER,
    version BIGINT DEFAULT 0
);
```

### Drivers Table

```sql
CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    driver_number INTEGER NOT NULL UNIQUE,
    nationality VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    team_id BIGINT NOT NULL REFERENCES teams(id),
    version BIGINT DEFAULT 0
);
```

## Sample Data

The application includes pre-populated data for the 2025 F1 season:

- **10 Teams**: Red Bull Racing, Mercedes, Ferrari, McLaren, Aston Martin, Alpine, Williams, RB, Kick Sauber, Haas F1 Team
- **20 Drivers**: All confirmed drivers for the 2025 season including Max Verstappen, Lewis Hamilton, Charles Leclerc, Lando Norris, and more

## Testing

### Run All Tests

```bash
./mvnw test
```

### Run Integration Tests Only

```bash
./mvnw test -Dtest="*IntegrationTest"
```

The integration tests use Testcontainers to spin up a PostgreSQL 18 Alpine container automatically.

## Project Structure

```
src/
├── main/
│   ├── java/com/f1/api/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── entity/           # JPA entities
│   │   ├── exception/        # Custom exceptions and handlers
│   │   ├── mapper/           # Entity-DTO mappers
│   │   ├── repository/       # Spring Data JPA repositories
│   │   ├── service/          # Business logic layer
│   │   └── F1Application.java
│   └── resources/
│       ├── db/migration/     # Flyway migration scripts
│       ├── openapi/          # OpenAPI specification
│       └── application.yml   # Application configuration
└── test/
    ├── java/com/f1/api/
    │   └── integration/      # Integration tests
    └── resources/
        └── application-test.yml
```

## API-First Development

This project follows API-first development principles:

1. **OpenAPI Specification**: The API is defined in `src/main/resources/openapi/f1-api.yaml`
2. **Code Generation**: API interfaces and models are generated during Maven build
3. **Implementation**: Controllers implement the generated interfaces
4. **Validation**: Request/response validation is enforced by generated code

To modify the API:
1. Update the OpenAPI specification file
2. Run `./mvnw clean compile` to regenerate interfaces
3. Update controller implementations if needed

## Development Tips

### Regenerate API Code

After modifying the OpenAPI specification:

```bash
./mvnw clean generate-sources
```

### Database Migrations

Flyway migrations are located in `src/main/resources/db/migration/`. To create a new migration:

1. Create a new file: `V{version}__Description.sql`
2. Add your SQL changes
3. Restart the application

### View Database

Connect to the running PostgreSQL container:

```bash
docker exec -it f1-postgres psql -U f1user -d f1db
```

Useful commands:
```sql
\dt              -- List tables
\d teams         -- Describe teams table
\d drivers       -- Describe drivers table
SELECT * FROM teams;
SELECT * FROM drivers;
```

## Configuration

Key configuration properties in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/f1db
    username: f1user
    password: f1pass

server:
  port: 8080
```

## CI/CD Considerations

- Tests use Testcontainers and require Docker to be available
- Build produces a single executable JAR: `target/f1-api-1.0.0-SNAPSHOT.jar`
- Health check endpoint available at: `/actuator/health` (if enabled)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `./mvnw test`
5. Submit a pull request

## License

This project is licensed under the terms specified in the LICENSE file.

## Support

For issues, questions, or contributions, please open an issue in the repository.
