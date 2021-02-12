package my.test.app.utils.ui.app;

import my.test.app.utils.ui.app.customwidget.ResultPageNavigationPanel;
import my.test.app.utils.ui.app.customwidget.SearchResultItem;
import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.PageObject;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class GoogleResultPage extends PageObject {

    final public ResultPageNavigationPanel navigation = new ResultPageNavigationPanel(Locator.create("//div[@id='foot']"));

    @Override
    protected Locator readyLocator() {
        return Locator.create("//div[@id='res' and @class='med']");
    }

    final public List<SearchResultItem> getResultItems() {
        List<SearchResultItem> result = new ArrayList<>();

        Locator searchResultLocator = Locator.create("//div[@id='search']//div[@class='g']");
        int count = this.wd().findElements(searchResultLocator.webDriverLocator()).size();
        for (int i = 1; i <= count; i++) {
            result.add(new SearchResultItem(searchResultLocator.createChild("../div[@class='g'][" + i + "]")));
        }
        return result;
    }

    public void clickOnPageNumber(String pageNumber) {
        Locator pageNumbers = Locator.create("//div[@id='foot']//a[@aria-label]");
        List<WebElement> listOfPageNumbers = this.wd().findElements(pageNumbers.webDriverLocator());
        for (WebElement pageNumberInList : listOfPageNumbers) {
            if (pageNumberInList.getAttribute("aria-label").contains(pageNumber)) {
                pageNumberInList.click();
                break;
            }
        }
    }
}
