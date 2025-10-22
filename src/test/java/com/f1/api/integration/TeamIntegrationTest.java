package com.f1.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;

public class TeamIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldGetAllTeams() {
        given()
                .when()
                .get("/teams")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(greaterThan(0)))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue());
    }

    @Test
    void shouldGetTeamById() {
        given()
                .when()
                .get("/teams/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("name", equalTo("Red Bull Racing"))
                .body("base", equalTo("Milton Keynes, United Kingdom"))
                .body("teamChief", equalTo("Christian Horner"))
                .body("powerUnit", equalTo("Honda RBPT"));
    }

    @Test
    void shouldReturnNotFoundForInvalidTeamId() {
        given()
                .when()
                .get("/teams/9999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("type", containsString("not-found"))
                .body("title", equalTo("Resource Not Found"))
                .body("status", equalTo(404));
    }

    @Test
    void shouldCreateNewTeam() {
        String requestBody = """
                {
                    "name": "Audi F1 Team",
                    "base": "Neuburg, Germany",
                    "teamChief": "Andreas Seidl",
                    "powerUnit": "Audi",
                    "firstEntry": 2026,
                    "championships": 0
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/teams")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("name", equalTo("Audi F1 Team"))
                .body("base", equalTo("Neuburg, Germany"))
                .body("teamChief", equalTo("Andreas Seidl"));
    }

    @Test
    void shouldReturnConflictWhenCreatingDuplicateTeam() {
        String requestBody = """
                {
                    "name": "Red Bull Racing",
                    "base": "Milton Keynes, UK",
                    "teamChief": "Christian Horner",
                    "powerUnit": "Honda RBPT",
                    "firstEntry": 2005,
                    "championships": 6
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/teams")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("type", containsString("conflict"))
                .body("title", equalTo("Resource Already Exists"))
                .body("status", equalTo(409));
    }

    @Test
    void shouldUpdateTeam() {
        String requestBody = """
                {
                    "name": "Mercedes-AMG Petronas F1 Team",
                    "base": "Brackley, United Kingdom",
                    "teamChief": "Toto Wolff",
                    "powerUnit": "Mercedes",
                    "firstEntry": 1970,
                    "championships": 8
                }
                """;

        given()
                .body(requestBody)
                .when()
                .put("/teams/2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(2))
                .body("name", equalTo("Mercedes-AMG Petronas F1 Team"))
                .body("teamChief", equalTo("Toto Wolff"));
    }

    @Test
    void shouldDeleteTeam() {
        // First create a team to delete
        String createBody = """
                {
                    "name": "Test Team to Delete",
                    "base": "Test Location",
                    "teamChief": "Test Chief",
                    "powerUnit": "Test Engine",
                    "firstEntry": 2024,
                    "championships": 0
                }
                """;

        Integer teamId = given()
                .body(createBody)
                .when()
                .post("/teams")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("id");

        // Delete the team
        given()
                .when()
                .delete("/teams/" + teamId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // Verify the team is deleted
        given()
                .when()
                .get("/teams/" + teamId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldGetDriversByTeam() {
        given()
                .when()
                .get("/teams/1/drivers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(2))
                .body("[0].teamId", equalTo(1))
                .body("[0].teamName", equalTo("Red Bull Racing"));
    }

    @Test
    void shouldReturnBadRequestForInvalidTeamData() {
        String requestBody = """
                {
                    "name": "",
                    "base": "Test Location",
                    "teamChief": "Test Chief",
                    "powerUnit": "Test Engine"
                }
                """;

        given()
                .body(requestBody)
                .when()
                .post("/teams")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("type", containsString("validation-error"))
                .body("status", equalTo(400));
    }
}
