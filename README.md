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

### Generating reports
#### Set up your code for reports
1. Go to [maven-cucumber-reporting](https://github.com/damianszczepanik/maven-cucumber-reporting) repo
2. Copy the full code block and paste it into your _pom.xml_ file, above your dependencies

The line `<inputDirectory>${project.build.directory}/jsonReports</inputDirectory>` means that it is expecting a JSON file as an input, this will be done in step 3. The file should be located in `target/jsonReports/`

3. Configure test runner to generate a JSON report: `plugin="json:target/jsonReports/cucumber-report.json"`

#### Generate the reports
The report generation is set to occur in the verify phase of maven
1. `cd` into project folder
2. Run the command `mvn test verify`
3. Results will be found in target folder
