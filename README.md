# REST-assured API test automation

This project contains code I wrote as part of a code-along from a course on Udemy.
The course was called "Rest API Testing (Automation) from Scratch-Rest Assured Java" and it was taught by "Rahul Shetty Academy".

## Tutorial: Run tests using Maven
Running tests in the IDE is good, but running them from the CLI using Maven is better because it enables CI tools like Jenkins to run the tests.

Steps to run tests from command line:
1. `cd` into project folder
2. Run the command `mvn test` to run all the test runners in `src/test/java`

### Filtering by tag
The tutorial advised that you can add `-D cucumber.options="--tags @AddPlace"` to only execute tests with the matching tag, though it didn't make a difference for me and the console said:

```
WARNING: Passing commandline options via the property 'cucumber.options' is no longer supported.
Please use individual properties instead. See the java doc on io.cucumber.core.options.Constants for details.
```

Check out these resources for more information:
- https://javadoc.io/doc/io.cucumber/cucumber-core/latest/io/cucumber/core/options/Constants.html
- https://github.com/cucumber/cucumber-jvm-starter-maven-java

## Tutorial: Generating reports
It has already been done in this project, but here are instructions to set up Maven to automatically generate test reports

### Set up your code for reports
1. Go to [maven-cucumber-reporting](https://github.com/damianszczepanik/maven-cucumber-reporting) repo
2. Copy the full code block and paste it into your _pom.xml_ file, above your dependencies

The line `<inputDirectory>${project.build.directory}/jsonReports</inputDirectory>` means that it is expecting a JSON file as an input, this will be done in step 3. The file should be located in `target/jsonReports/`

3. Configure test runner to generate a JSON report: `plugin="json:target/jsonReports/cucumber-report.json"`

#### Generate the reports
The report generation is set to occur in the verify phase of maven
1. `cd` into project folder
2. Run the command `mvn test verify`
3. Results will be found in target folder

## Tutorial: Using Jenkins
You can use Jenkins as a CI tool to automatically build and test the system

### Installing Jenkins
Jenkins can be installed on your local system using a .war file from [here](https://www.jenkins.io/doc/book/installing/war-file/)

1. Download the latest Jenkins WAR file to an appropriate directory on your machine
2. In PowerShell, `cd` to the download directory
3. Run the command `java -jar jenkins.war`
    - You can specify a specific port using the flag `-httpPort=<port number>`
4. Browse to http://localhost:8080 and wait until the Unlock Jenkins page appears

Run through the initial setup wizard and then you're done

### Setting up a job to build project
1. New item
2. Freestyle project
3. In the general settings:
    1. Click "Use custom workspace"
    2. Set directory to the filepath of the project folder
4. In build steps:
    1. Click "add build step", select invoke top-level maven targets
    2. In Goals put the `test verify` (this is equivalent to `mvn test verify`)
5. Click save
6. You can now click "Build now"
7. You can see the full project files and test reports in the workspace tab

### Adding parameterisation
Currently, if you wanted to change the command to only run tests with a particular tag, you have to change this in settings.

You can use parameterisation to make this simpler

1. In your job click "Configure"
2. In the general settings clock "This project is parameterized"
    1. Click "Add parameter", select choice parameter
    2. Set name to `tag`
    3. In the choices box, put all the names of all the tags in the project
3. In the build steps settings, change the command in goals to replace the hard coded value with the name of the tag, with a `$` prepended and wrapped in double quotes
    - So `test verify -Dcucumber.options="--tags @AddPlace"`...
    - Would become `test verify -Dcucumber.options="--tags @"$tag""`
