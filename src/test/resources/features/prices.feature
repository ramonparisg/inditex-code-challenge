Feature: Price feature

  Scenario: Scenario 1
    Given request at 2020-06-14T10:00:00 for product 35455 and brand 1
    When I get the price
    Then the response should contain xxx