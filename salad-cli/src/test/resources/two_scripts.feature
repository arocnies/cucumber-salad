Feature: Load *.step.kts

  Scenario: Two scripts are loaded
    Given first step is included
    And second step is included

  Scenario: Printing properties
    When the salad.yml properties are printed