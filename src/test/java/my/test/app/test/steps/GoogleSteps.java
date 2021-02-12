package my.test.app.test.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my.test.app.utils.base.Configuration;
import my.test.app.utils.ui.Session;
import my.test.app.utils.ui.app.GoogleMainPage;
import my.test.app.utils.ui.app.GoogleResultPage;
import my.test.app.utils.ui.app.customwidget.ResultPageNavigationItem;
import my.test.app.utils.ui.app.customwidget.SearchResultItem;
import org.junit.Assert;

public class GoogleSteps {
    @Given("^I Open Google Search Page$")
    public void iOpenGoogleSearchPage() {
        Session.get().wd().get(Configuration.get().SITE_URL.asString());
        //TO DO smth
    }

    @And("^I enter value \"(.+?)\" to sear field$")
    public void iEnterValueToSearch(String value) {
        GoogleMainPage mainPage = new GoogleMainPage();
        GoogleResultPage resultPage = mainPage.search(value);
        resultPage.confirmPage();
    }

    @Then("^Link to site \"(.+?)\" exists$")
    public void linkToSiteExist(String site) {
        boolean found = false;
        GoogleResultPage resultPage = new GoogleResultPage();
        for (SearchResultItem item : resultPage.getResultItems()) {
            if (item.site().equalsIgnoreCase(site)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Site \"" + site + "\" not found", found);
    }

    @When("^I click on page number \"(.+?)\"$")
    public void iClickOnPageNumber(String pageNumber) {
        GoogleResultPage resultPage = new GoogleResultPage();
//        resultPage.clickOnPageNumber(pageNumber);
        for (ResultPageNavigationItem item : resultPage.navigation.getPages()) {
            System.out.println(item);
            if (item.getPageNumber().equalsIgnoreCase((pageNumber))) {
                item.click();
                break;
            }
        }
    }

    @Then("^Site description \"(.+?)\" exists$")
    public void siteDescriptionExists(String description) {
        boolean found = false;
        GoogleResultPage resultPage = new GoogleResultPage();
        for (SearchResultItem item : resultPage.getResultItems()) {
            System.out.println(item.getDescriptionText());
            if (item.getDescriptionText().contains(description)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Description \"" + description + "\" not found", found);
    }
}
