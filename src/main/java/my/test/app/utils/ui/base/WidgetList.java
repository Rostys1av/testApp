package my.test.app.utils.ui.base;

import my.test.app.utils.ui.Session;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class WidgetList {
    private WebDriver _driver;

    static private WidgetList _instance;

    private WidgetList() {
        this._driver = Session.get().wd();
    }

    static public WidgetList get() {
        if (_instance == null) {
            _instance = new WidgetList();
        }
        return _instance;
    }


    final public <T extends Widget> List<T> getList(Class<T> clazz, Locator locator) {
        List<T> result = new ArrayList<>();
        List<WebElement> elements = this._driver.findElements(locator.webDriverLocator());
        for (int num = 1; num <= elements.size(); num++) {
            Locator wLocator = locator.createChild("(./self::node())[" + num + "]");
            try {
                Constructor<T> constructor = clazz.getConstructor(Locator.class);
                T widget = constructor.newInstance(wLocator);
                result.add(widget);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}