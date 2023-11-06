package net.bddtrader.acceptancetests;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class WhenGettingCompanyDetails {

    @Before
    public void prepare_rest_config() {
        RestAssured.baseURI = "http://localhost:9000/api/";
    }

    @Test
    public void should_return_name_and_sector() {
        RestAssured.get("stock/aapl/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
}
