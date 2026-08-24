package com.mattcorth.cucumbertests.stepdefinitions;

import com.mattcorth.cucumbertests.pojos.AddPlace;
import com.mattcorth.cucumbertests.pojos.Location;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlaceValidationStepDefs {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;
    //TestDataBuild data =new TestDataBuild();
    static String place_id;

    @Given("add place payload")
    public void add_place_payload() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace p =  new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhoneNumber("(+91) 983 893 3937");
        p.setWebsite("https://rahulshettyacademy.com");
        p.setName("Frontline house");
        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");

        p.setTypes(myList);
        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);

        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        resspec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        res = RestAssured.given().spec(req).body(p);
    }
    @When("user calls {string} with Post http request")
    public void user_calls_with_post_http_request(String string) {
        response = res.when().post("/maps/api/place/add/json")
                .then()
                    .spec(resspec)
                    .log().all()
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
