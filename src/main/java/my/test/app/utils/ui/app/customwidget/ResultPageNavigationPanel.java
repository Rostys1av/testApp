package my.test.app.utils.ui.app.customwidget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;
import my.test.app.utils.ui.base.WidgetList;

import java.util.List;

public class ResultPageNavigationPanel extends Widget {
    public ResultPageNavigationPanel(Locator locator) {
        super(locator);
    }

    final public List<ResultPageNavigationItem> getPages() {
        return WidgetList.get().getList(ResultPageNavigationItem.class, this.locator().createChild(".//table//td[./a and not(@role='heading')]/a"));
    }
}
