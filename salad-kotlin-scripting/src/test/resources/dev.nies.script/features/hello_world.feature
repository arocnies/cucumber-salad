Feature: Hello Cucumber

  Scenario: I say hello
    Given I know a name
    When I say hello
    Then the output should contain the name
