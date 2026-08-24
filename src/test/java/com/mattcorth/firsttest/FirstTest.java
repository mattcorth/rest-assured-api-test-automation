package com.mattcorth.firsttest;

import io.restassured.RestAssured;

public class FirstTest {
    public static void main(String[] args) {
        // There are three steps to method calls using rest assured:
        // given() - input request details
        // when()  - submit request
        // then()  - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        RestAssured
                .given()
                    .queryParam("key", "qaclick123")
                    .header("Content-Type", "application/json")
                    .body("{\r\n" +
                            "  \"location\": {\r\n" +
                            "    \"lat\": -38.383494, \r\n" +
                            "    \"lng\": 33.427362\r\n" +
                            "  },\r\n" +
                            "  \"accuracy\": 50,\r\n" +
                            "  \"name\": \"Rahul Shetty Academy\",\r\n" +
                            "  \"phone_number\": \"(+91) 983 893 3937\", \r\n" +
                            "  \"address\": \"29, side layout, cohen 09\", \r\n" +
                            "  \"types\": [\r\n" +
                            "    \"shoe park\", \r\n" +
                            "    \"shop\"\r\n" +
                            "  ], \r\n" +
                            "  \"website\": \"http://rahulshettyacademy.com\",\r\n" +
                            "  \"language\": \"French-IN\"\r\n" +
                            "}\r\n")
                .when()
                    .post("maps/api/place/add/json")
                .then()
                    .log().all()
                    .assertThat().statusCode(200);
    }
}
