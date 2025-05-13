@all
Feature: get device info by service

  @getDevice
  Scenario Outline: get info
    When i call service for id "<id>"
    Then i can validate response
      | productName   | productPrice   |
      | <productName> | <productPrice> |
    Examples:
      | id | productName            | productPrice |
      | 7  | Apple MacBook Pro 16   | 1849.99      |
      | 5  | Samsung Galaxy Z Fold2 | 689.99       |

    @saveResponse
  Scenario: get all info
    When i call service
    Then i can save response
