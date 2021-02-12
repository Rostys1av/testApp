package my.test.app.utils.ui.app.customwidget;

import my.test.app.utils.ui.base.Locator;
import my.test.app.utils.ui.base.Widget;
import my.test.app.utils.ui.widget.HTMLElement;
import my.test.app.utils.ui.widget.Link;

public class SearchResultItem extends Widget {

    private Link _link = new Link(this.locator().createChild(".//a/h3"));
    private HTMLElement _site = new HTMLElement(this.locator().createChild(".//a//cite"));
    private HTMLElement _descriptionText = new HTMLElement(this.locator().createChild(".//div[@class='s']"));

    public SearchResultItem(Locator locator) {
        super(locator);
    }

    final public String site(){
        return this._site.getText();
    }

    final public String getDescriptionText() {
        return this._descriptionText.getText();
    }


}
