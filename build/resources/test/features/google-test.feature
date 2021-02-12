Feature: Go to google page

  @googleTest
  Scenario: Google search
    Given I Open Google Search Page
    And I enter value "test" to sear field
    Then Link to site "www.speedtest.net" exists
    When I click on page number "5"
    Then Site description "Widespread testing" exists
    #TODO add navigation by pages
    #Find result element by description