package com.mattcorth.cucumbertests.stepdefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    // This will run before the DeletePlace scenario
    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        PlaceValidationStepDefs stepDefs = new PlaceValidationStepDefs();
        // Execute this code only when place_id is null
        if (PlaceValidationStepDefs.placeId == null) {
            // Write code that will set the place_id
            stepDefs.add_place_payload_with("Matt", "English", "United Kingdom");
            stepDefs.user_calls_with_http_request("addPlaceAPI", "POST");
            stepDefs.verify_that_created_place_id_maps_to_using("Matt", "getPlaceAPI");
        }
    }
}
