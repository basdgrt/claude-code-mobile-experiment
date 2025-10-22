package com.f1.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;

public class DriverIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldGetAllDrivers() {
        given()
                .when()
                .get("/drivers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].firstName", notNullValue())
                .body("[0].lastName", notNullValue())
                .body("[0].teamName", notNullValue());
    }

    @Test
    void shouldGetDriversByTeamId() {
        given()
                .queryParam("teamId", 1)
                .when()
                .get("/drivers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("[0].teamId", equalTo(1))
                .body("[0].teamName", equalTo("Red Bull Racing"));
    }

    @Test
    void shouldGetDriverById() {
        given()
                .when()
                .get("/drivers/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("firstName", equalTo("Max"))
                .body("lastName", equalTo("Verstappen"))
                .body("driverNumber", equalTo(1))
                .body("nationality", equalTo("Dutch"))
                .body("teamId", equalTo(1))
                .body("teamName", equalTo("Red Bull Racing"));
    }

    @Test
    void shouldReturnNotFoundForInvalidDriverId() {
        given()
                .when()
                .get("/drivers/9999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("type", containsString("not-found"))
                .body("title", equalTo("Resource Not Found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldCreateNewDriver() {
        String requestBody = """
                {
                    "firstName": "Test",
                    "lastName": "Driver",
                    "driverNumber": 99,
                    "nationality": "British",
                    "dateOfBirth": "2000-01-01",
                    "teamId": 1
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/drivers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("firstName", equalTo("Test"))
                .body("lastName", equalTo("Driver"))
                .body("driverNumber", equalTo(99))
                .body("nationality", equalTo("British"))
                .body("teamId", equalTo(1))
                .body("teamName", equalTo("Red Bull Racing"));
    }

    @Test
    void shouldReturnConflictWhenCreatingDriverWithDuplicateNumber() {
        String requestBody = """
                {
                    "firstName": "Another",
                    "lastName": "Driver",
                    "driverNumber": 1,
                    "nationality": "British",
                    "dateOfBirth": "2000-01-01",
                    "teamId": 1
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/drivers")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("type", containsString("conflict"))
                .body("title", equalTo("Resource Already Exists"))
                .body("status", equalTo(409));
    }

    @Test
    void shouldUpdateDriver() {
        String requestBody = """
                {
                    "firstName": "Lewis",
                    "lastName": "Hamilton",
                    "driverNumber": 44,
                    "nationality": "British",
                    "dateOfBirth": "1985-01-07",
                    "teamId": 3
                }
                """;

        given()
                .body(requestBody)
                .when()
                .put("/drivers/6")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(6))
                .body("firstName", equalTo("Lewis"))
                .body("lastName", equalTo("Hamilton"))
                .body("driverNumber", equalTo(44))
                .body("teamId", equalTo(3))
                .body("teamName", equalTo("Ferrari"));
    }

    @Test
    void shouldDeleteDriver() {
        // First create a driver to delete
        String createBody = """
                {
                    "firstName": "Delete",
                    "lastName": "Test",
                    "driverNumber": 98,
                    "nationality": "Test",
                    "dateOfBirth": "2000-01-01",
                    "teamId": 1
                }
                """;

        Integer driverId = given()
                .body(createBody)
                .when()
                .post("/drivers")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Delete the driver
        given()
                .when()
                .delete("/drivers/" + driverId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Verify the driver is deleted
        given()
                .when()
                .get("/drivers/" + driverId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldReturnBadRequestForInvalidDriverData() {
        String requestBody = """
                {
                    "firstName": "",
                    "lastName": "Test",
                    "driverNumber": 100,
                    "nationality": "Test",
                    "teamId": 1
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/drivers")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("type", containsString("validation-error"))
                .body("status", equalTo(400));
    }

    @Test
    void shouldReturnNotFoundWhenCreatingDriverWithInvalidTeamId() {
        String requestBody = """
                {
                    "firstName": "Test",
                    "lastName": "Driver",
                    "driverNumber": 97,
                    "nationality": "British",
                    "dateOfBirth": "2000-01-01",
                    "teamId": 9999
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/drivers")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("type", containsString("not-found"))
                .body("status", equalTo(404));
    }
}
