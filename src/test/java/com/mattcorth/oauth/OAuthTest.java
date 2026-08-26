package com.mattcorth.oauth;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

// This file is more of a demonstration of the various steps involved when testing
//   an api that uses OAuth 2.0
public class OAuthTest {
    private static final String DRIVER_LOCATION = "src/test/resources/selenium/chromedriver.exe";

    public static void main(String[] args) throws IOException, InterruptedException {

        OAuthTest test = new OAuthTest();

        // Authenticate with Google authentication service and return auth code
        // NOTE: Google does not allow you to do this through browser automation anymore,
        //         the url must be retrieved by carrying out the steps manually
//        String code = test.getAuthCode();
        String code = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";

        // Use the auth code to hit the Google resource server storing user data,
        //   and extract the access token
        String accessToken = test.getAccessToken(code);

        // You can now use the access token for authentication in future requests
        String response = RestAssured
                .given()
                    .queryParam("access_token", accessToken)
                .when()
                    .log().all()
                    .get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response);
    }

    // Authenticates with authentication server using username and password
    public String getAuthCode() throws IOException, InterruptedException {
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();

        WebDriver webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());

        // This url represents the one that you are redirected to when you click "sign in with Google"
        webDriver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");

        // Enter username
        webDriver.findElement(By.cssSelector("input[type='text']")).sendKeys("EMAIL HERE");
        webDriver.findElement(By.cssSelector("input[jsname='V67aGc']")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        // Enter password
        webDriver.findElement(By.cssSelector("input[type='password']")).sendKeys("PASSWORD HERE");
        webDriver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);

        // Extract the authentication code from the url of the page you are redirected to
        String partialCode = webDriver.getCurrentUrl().split("code=")[1];
        return partialCode.split("&scope")[0];
    }

    // Using the auth code, hit the resource server to get an access token
    public String getAccessToken(String code) {
        String accessTokenResponse = RestAssured
                .given()
                    .urlEncodingEnabled(false) // otherwise % characters are converted to their numerical equivalent
                    .queryParam("code", code)
                    .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                    .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                    .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                    .queryParam("grant_type", "authorization_code")
                .when()
                    .log().all()
                    .post("https://www.googleapis.com/oauth2/v4/token").asString();

        // Extract access_token
        JsonPath js = new JsonPath(accessTokenResponse);
        return js.getString("access_token");
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
//        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
//        options.setImplicitWaitTimeout(Duration.ofSeconds(10)); // wait 10 seconds for everything

        return options;
    }
}
