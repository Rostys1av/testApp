package my.test.app.utils.ui.widget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;
import org.openqa.selenium.Keys;

public class TextBox extends Widget {
    public TextBox(Locator locator) {
        super(locator);
    }

    final public void setValue(String value){
        this.element().clear();
        this.element().sendKeys(value);
    }

    final public void pressEnter(){
        this.element().sendKeys(Keys.ENTER);
    }

    final public String getValue(){
        return this.element().getAttribute("value");
    }
}
