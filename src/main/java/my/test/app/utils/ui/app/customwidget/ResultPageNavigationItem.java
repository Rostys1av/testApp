package my.test.app.utils.ui.app.customwidget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;

public class ResultPageNavigationItem extends Widget {
    public ResultPageNavigationItem(Locator locator) {
        super(locator);
    }

    final public String getPageNumber(){
        return this.element().getText();
    }

    final public void click(){
        super.click();
    }
}
