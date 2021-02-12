package my.test.app.utils.ui.widget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;

public class HTMLElement extends Widget {
    public HTMLElement(Locator locator) {
        super(locator);
    }

    final public void click(){
        super.click();
    }

    final public String getText(){
        return this.element().getText();
    }
}
