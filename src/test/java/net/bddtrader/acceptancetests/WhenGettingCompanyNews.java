package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class WhenGettingCompanyNews {

    @Before
    public void prepare_rest_config() {
        RestAssured.baseURI = "http://localhost:9000/api/";
    }

    @Test
    public void should_return_name_and_sector() {
        RestAssured.get("/stock/aapl/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }

    @Test
    public void should_return_name_and_sector_from_local() {
        RestAssured.get("http://localhost:9000/api/stock/aapl/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }

    @Test
    public void should_return_news_for_a_requested_company() {
        RestAssured.given().queryParam("symbols","AAPL")
                .when()
                .get("/news")
                .then()
                .body("related", everyItem(containsString("AAPL")));
    }

}
