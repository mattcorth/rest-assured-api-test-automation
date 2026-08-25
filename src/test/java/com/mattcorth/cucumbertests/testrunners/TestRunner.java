package com.mattcorth.cucumbertests.testrunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        //tags = "@DeletePlace",
        glue = {"com.mattcorth.cucumbertests.stepdefinitions"}
)
public class TestRunner {

}
