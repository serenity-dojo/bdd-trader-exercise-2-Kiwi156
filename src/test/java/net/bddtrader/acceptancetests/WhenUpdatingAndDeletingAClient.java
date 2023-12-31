package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.bddtrader.clients.Client;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class WhenUpdatingAndDeletingAClient {
    @Before
    public void setupBaseUrl() {
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api/";
    }

    @Test
    public void should_be_able_to_delete_a_client() {
        // Given a client exists
        String id = aClientExists(Client.withFirstName("Pam").andLastName("Beasley").andEmail("pam@beasly.com"));

        // When I delete the client
        RestAssured.given().delete("/client/{id}", id);

        // Then the client should no longer exist
        RestAssured.given()
                .auth().basic("user", "password")
                .get("/client/{id}", id)
                .then()
                .statusCode(404);
    }

    @Test
    public void should_be_able_to_update_a_client() {
        Client pam = Client.withFirstName("Pam").andLastName("Beasley").andEmail("pam@beasly.com");
        // Given a client exists
        String id = aClientExists(pam);

        // When I update the email address of a client

        Client pamWithUpdates = Client.withFirstName("Pam").andLastName("Beasley").andEmail("pam@gmail.com");

        RestAssured.given().contentType(ContentType.JSON)
                .and().body(pamWithUpdates)
                .when().put("/client/{id}", id)
                .then().statusCode(200);

        RestAssured.when().get("/client/{id}", id)
                .then().body("email", equalTo("pam@gmail.com"));
    }

    private String aClientExists(Client existingClient) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(existingClient)
                .when()
                .post("/client")
                .jsonPath().getString("id");
    }
}
