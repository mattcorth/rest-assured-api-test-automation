package com.mattcorth.cucumbertests.stepdefinitions;

import com.mattcorth.cucumbertests.resources.APIResource;
import com.mattcorth.cucumbertests.resources.TestDataBuild;
import com.mattcorth.cucumbertests.resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PlaceValidationStepDefs extends Utils {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data = new TestDataBuild();
    // Static means the data will persist between Cucumber scenarios
    static String placeId;

    @Given("add place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {
        res = RestAssured.given()
                .spec(requestSpecification())
                .body(data.addPlacePayload(name, language, address));
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String method) {
        APIResource r = APIResource.valueOf(resource);

        resspec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        if (method.equalsIgnoreCase("POST"))
            response = res.when().post(r.getEndpoint());
        else if (method.equalsIgnoreCase("GET"))
            response = res.when().get(r.getEndpoint());
    }
    @Then("the API call was successful with status code {int}")
    public void the_api_call_was_successful_with_status_code(Integer int1) {
        assertEquals(200,response.getStatusCode());
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expected) {
        assertEquals(expected, getJsonPath(response, key));
    }

    @Then("verify that created place_Id maps to {string} using {string}")
    public void verify_that_created_place_id_maps_to_using(String expectedName, String resource) throws IOException {
        placeId = getJsonPath(response, "place_id");
        res = RestAssured.given().spec(requestSpecification()).queryParam("place_id", placeId);
        user_calls_with_http_request(resource, "GET");
        String actualName = getJsonPath(response, "name");
        assertEquals(expectedName, actualName);
    }

    @Given("DeletePlace payload")
    public void delete_place_payload() throws IOException {
        res = RestAssured
                .given()
                    .spec(requestSpecification())
                    .body(data.deletePlacePayload(placeId));
    }
}
