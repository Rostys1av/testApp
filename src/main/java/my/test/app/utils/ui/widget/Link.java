package my.test.app.utils.ui.widget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;

public class Link extends Widget {
    public Link(Locator locator) {
        super(locator);
    }

    final public void click(){
        this.element().click();
    }

    final public String getText(){
        return this.element().getText();
    }

}
