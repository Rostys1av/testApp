package my.test.app.utils.ui.widget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;

public class Button extends Widget {
    public Button(Locator locator) {
        super(locator);
    }

    final public void click(){
        this.element().click();
    }
}
