Feature: Price feature

  Scenario: Get price that match with only one filter
    Given request at "2020-06-14T10:00:00" for product 35455 and brand 1
    When I get the price
    Then the response should be price id 1, price 35.50 and currency "EUR"

  Scenario: Get price with higher priority
    Given request at "2020-06-14T16:00:00" for product 35455 and brand 1
    When I get the price
    Then the response should be price id 2, price 25.45 and currency "EUR"

  Scenario: Scenario 3
    Given request at "2020-06-14T21:00:00" for product 35455 and brand 1
    When I get the price
    Then the response should be price id 1, price 35.50 and currency "EUR"

  Scenario: Scenario 4
    Given request at "2020-06-15T10:00:00" for product 35455 and brand 1
    When I get the price
    Then the response should be price id 3, price 30.50 and currency "EUR"

  Scenario: Scenario 5
    Given request at "2020-06-16T21:00:00" for product 35455 and brand 1
    When I get the price
    Then the response should be price id 4, price 38.95 and currency "EUR"

  Scenario: Price doesn't exist for the filter
    Given request at "2020-06-10T21:00:00" for product 35455 and brand 1
    When I get the price
    Then price not found