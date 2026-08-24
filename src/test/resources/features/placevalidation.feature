Feature: Validating place APIs

  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given add place payload with "<name>" "<language>" "<address>"
    When user calls "addPlaceAPI" with "Post" http request
    Then the API call is successful
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify that created place_Id maps to "<name>" using "getPlaceAPI"

    Examples:
    | name            | language  | address                   |
    | Frontline house | French-IN | 29, side layout, cohen 09 |
    | AAHouse         | English   | World cross centre        |
