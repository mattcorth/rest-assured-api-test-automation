package com.mattcorth.cucumbertests.resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Utils {
    RequestSpecification req;

    public RequestSpecification requestSpecification() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        return req;
    }
}
