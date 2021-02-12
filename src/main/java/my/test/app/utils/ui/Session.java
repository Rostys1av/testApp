package my.test.app.utils.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Session {
    static private Session _instance;

    private Session() {
    }

    static public Session get() {
        if (_instance == null)
            _instance = new Session();
        return _instance;

    }

    private WebDriver _driver;

    public WebDriver wd() {
        if (this._driver == null) {
            WebDriverManager.chromedriver().setup();
            this._driver = new ChromeDriver();
        }
        return this._driver;
    }
}
