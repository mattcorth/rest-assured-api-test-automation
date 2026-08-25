# REST-assured API test automation

This project contains code I wrote as part of a code-along from a course on Udemy.
The course was called "Rest API Testing (Automation) from Scratch-Rest Assured Java" and it was taught by "Rahul Shetty Academy".

### Run tests using Maven
Running tests in the IDE is good, but running them from the CLI using Maven is better because it enables CI tools like Jenkins to run the tests.

Steps to run tests from command line:
1. `cd` into project folder
2. Run the command `mvn test` to run all the test runners in `src/test/java`

#### Filtering by tag
The tutorial advised that you can add `-D cucumber.options="--tags @AddPlace"` to only execute tests with the matching tag, though it didn't make a difference for me and the console said:

```
WARNING: Passing commandline options via the property 'cucumber.options' is no longer supported.
Please use individual properties instead. See the java doc on io.cucumber.core.options.Constants for details.
```

Check out these resources for more information:
- https://javadoc.io/doc/io.cucumber/cucumber-core/latest/io/cucumber/core/options/Constants.html
- https://github.com/cucumber/cucumber-jvm-starter-maven-java
