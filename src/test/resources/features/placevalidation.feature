Feature: Validating place APIs

  Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
    Given add place payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with Post http request
    Then the API call is successful
    And "status" in response body is "OK"
    And "scope" in response body is "APP"

    Examples:
    | name            | language  | address                   |
    | Frontline house | French-IN | 29, side layout, cohen 09 |
    | AAHouse         | English   | World cross centre        |
