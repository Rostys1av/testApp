package my.test.app.utils.ui.base;

import my.test.app.utils.Sleep;
import my.test.app.utils.ui.Session;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Instant;

abstract public class PageObject {
    final private Locator _locator;

    public PageObject() {
        this._locator = this.readyLocator();
    }

    final protected WebDriver wd() {
        return Session.get().wd();
    }

    final protected Locator locator() {
        return this._locator;
    }

    public void confirmPage() {
        long endTime = Instant.now().getEpochSecond() + 30;
        WebElement element = null;
        while (true) {
            try {
                element = this.wd().findElement(this._locator.webDriverLocator());
            } catch (Throwable e) {/**/}
            if (element != null || endTime <= Instant.now().getEpochSecond()) {
                break;
            }
            Sleep.millis(500);
        }
        if (element == null) {
            throw new RuntimeException("Can't confirm that page loaded" + this.getClass().getSimpleName()
                    + "[" + _locator + "] ");
        }
    }

    abstract protected Locator readyLocator();
}
