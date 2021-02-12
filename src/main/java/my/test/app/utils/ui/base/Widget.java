package my.test.app.utils.ui.base;

import my.test.app.utils.Sleep;
import my.test.app.utils.ui.Session;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Widget {
    private static final int ELEMENT_TIMEOUT_SEC = 5;
    final protected WebDriver _driver;
    final private Locator _locator;

    public Widget(Locator locator) {
        this._locator = locator;
        this._driver = Session.get().wd();
    }

    final public Locator locator() {
        return this._locator;
    }

    protected void click() {
        this.scrollToElement().click();
    }

    final protected WebElement scrollToElement() {
        WebElement element = this.element();
        JavascriptExecutor js = (JavascriptExecutor) this._driver;
        js.executeScript("arguments[0].scrollIntoView(false);", element);
        return element;
    }

    final public WebElement element() {
        return this.element(ELEMENT_TIMEOUT_SEC); // wait for loading and rendering
    }

    final public WebElement element(int timeoutSeconds) {
        WebElement result = null;
        long endTime = Instant.now().getEpochSecond() + timeoutSeconds;
        while (true) {
            try {
                _driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
                // Wait ready state
                WebDriverWait wait = new WebDriverWait(this._driver, timeoutSeconds);
                wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) this._driver)
                        .executeScript("return document.readyState").equals("complete"));
                // Locator nesting
                SearchContext context = getLocatorContext(_driver, _locator);
                result = context.findElement(this._locator.webDriverLocator());
                // Wait visibility
                wait.until(ExpectedConditions.visibilityOf(result));
                _driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            } catch (Throwable e) { /* Ignore */ }
            if (result != null || timeoutSeconds == 0 || endTime <= Instant.now().getEpochSecond())
                break;
            Sleep.millis(500);
        }
        if (result == null)
            throw new RuntimeException("Element not found or not visible: " + this.locator());
        return result;
    }

    final public boolean isExist() {
        return this.isExist(ELEMENT_TIMEOUT_SEC);
    }

    final public boolean isExist(int timeoutSeconds) {
        boolean result = false;
        try {
            this.element(timeoutSeconds);
            result = true;
        } catch (Throwable e) { /* Ignore */ }
        return result;
    }

    final public void moveCursorToElement() {
        this.moveCursorToElement(0, 0);
    }

    final public void moveCursorToElement(int xOffset, int yOffset) {
        this.scrollToElement();
        Actions action = new Actions(this._driver);
        action.moveToElement(this.element(), xOffset, yOffset).build().perform();
    }

    static private SearchContext getLocatorContext(WebDriver driver, Locator locator) {
        if (locator.hasParent()) {
            Locator parent = locator.parent();
            SearchContext context = getLocatorContext(driver, parent);
            return context.findElement(parent.webDriverLocator());
        } else {
            return driver;
        }
    }
}