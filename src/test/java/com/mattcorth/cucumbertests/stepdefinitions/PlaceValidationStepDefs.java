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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class PlaceValidationStepDefs extends Utils {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    TestDataBuild data =new TestDataBuild();
    static String place_id;

    @Given("add place payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {
        res = RestAssured.given()
                .spec(requestSpecification())
                .body(data.addPlacePayload(name, language, address));
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String resource, String restVerb) {
        APIResource r = APIResource.valueOf(resource);

        resspec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        response = res.when().post(r.getEndpoint())
                .then()
                    .spec(resspec)
                    .extract().response();
    }
    @Then("the API call is successful")
    public void the_api_call_is_successful() {
        assertEquals(200,response.getStatusCode());
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expected) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(expected, js.get(key).toString());
    }
}
