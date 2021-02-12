package my.test.app.utils.ui.app;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.PageObject;
import my.test.app.utils.ui.widget.TextBox;

public class GoogleMainPage extends PageObject {

    final private TextBox _searchField = new TextBox(Locator.create("//input[@name='q']"));

    @Override
    protected Locator readyLocator() {
        return Locator.create("//div[@id='logo']");
    }

    final public GoogleResultPage search(String text) {
        this._searchField.setValue(text);
        this._searchField.pressEnter();
        return new GoogleResultPage();
    }
}
