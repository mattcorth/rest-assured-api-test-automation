package com.mattcorth.cucumbertests.testrunners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features",
        glue= {"com.mattcorth.cucumbertests.stepdefinitions"})
public class TestRunner {

}
