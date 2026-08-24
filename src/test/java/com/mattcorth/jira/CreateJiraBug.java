package com.mattcorth.jira;

/*
 * Jira is a project management tool
 * You can use it to design and prepare your project including test cases,
 *   user stories, and creating bugs
 *
 * API documentation: https://developer.atlassian.com/cloud/jira/platform/rest/v3/intro/#about
 * */


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

// A class to create a bug in Jira and attach a screenshot
public class CreateJiraBug {
    // We will use Basic Authentication: https://developer.atlassian.com/cloud/jira/platform/basic-auth-for-rest-apis/

    // A 'bug' is a type of 'issue' in Jira, so we want the /issue endpoint in the API
    // https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issues/#api-rest-api-3-issue-post

    public static void main(String[] args) {
        String requestBody = """
                {
                    "fields": {
                        "project": {
                            "key": "SCRUM"
                        },
                        "summary": "Dropdowns are not working",
                        "issuetype": {
                            "name": "Bug"
                        }
                    }
                }
                """;

        RestAssured.baseURI = "https://mattcorthorne-team.atlassian.net/";
        String createIssueResponse = RestAssured
                .given()
                    .contentType("application/json")
                    .header("Authorization", "Basic bWF0dGNvcnRob3JuZUBnbWFpbC5jb206QVRBVFQzeEZmR0YwNE1lRmxxbkJmeXlRQVlTZzFUSVZ5YjZta19uYlE3RVRlV2p4Rm41cGgyTDRUd2V4NDkxNmxyd2p4RFVMQ0w3dHg4TTZ3bERmZklLMFU0SkkzTFlPT0R2WmtqOXM5NjNYLVVnWUVfdTRWOU9lVkJoYVJ1a0VDc3NtTTFOQjEyUG1Fa2xyaXByRmRrZWdvMXE0ak04cVo1ZmJ0T21sZVhBcmY5M1pPVFJRZDJjPTJCMzg2Q0Q1")
                    .body(requestBody)
                .when()
                    .post("rest/api/3/issue")
                .then()
                    .log().all()
                    .assertThat().statusCode(201)
                    .extract().response().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        String issueId = js.getString("id");
        System.out.println("Issue ID: " + issueId);

        RestAssured
                .given()
                    .header("X-Atlassian-Token", "no-check")
                    .header("Authorization", "Basic bWF0dGNvcnRob3JuZUBnbWFpbC5jb206QVRBVFQzeEZmR0YwNE1lRmxxbkJmeXlRQVlTZzFUSVZ5YjZta19uYlE3RVRlV2p4Rm41cGgyTDRUd2V4NDkxNmxyd2p4RFVMQ0w3dHg4TTZ3bERmZklLMFU0SkkzTFlPT0R2WmtqOXM5NjNYLVVnWUVfdTRWOU9lVkJoYVJ1a0VDc3NtTTFOQjEyUG1Fa2xyaXByRmRrZWdvMXE0ak04cVo1ZmJ0T21sZVhBcmY5M1pPVFJRZDJjPTJCMzg2Q0Q1")
                    .multiPart("file", new File("/Users/mattc/Downloads/wrench-image.svg"))
                    .pathParam("key", issueId)
                .when()
                    .post("rest/api/3/issue/{key}/attachments")
                .then()
                    .log().all()
                    .assertThat().statusCode(200);
    }
}
