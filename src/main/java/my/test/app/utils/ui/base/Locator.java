package my.test.app.utils.ui.base;

import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Locator {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Static
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static private By parseLocator (String locatorString)
    {
        final int flags = Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.UNICODE_CASE;
        // Try xPath pattern
        Pattern p = Pattern.compile("^\\(*((:?\\/\\/)|(:?\\/)|(:?\\.{1,2}\\/)).+?$", flags);
        Matcher m = p.matcher(locatorString);
        if (m.matches())
            return By.xpath(locatorString);
        // Try CSS pattern
        p = Pattern.compile("^(#|\\.|(:?[\\w]+?[\\[\\s#\\.])).+?$", flags);
        m = p.matcher(locatorString);
        if (m.matches())
            return By.cssSelector(locatorString);
        throw new RuntimeException("Invalid locator");
    }
    static public Locator create (String locatorString)
    {
        return new Locator(null, locatorString);
    }
    static public Locator create (Locator parent, String locatorString)
    {
        return new Locator(parent, locatorString);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Object
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    final private String    _locatorString;
    final private By        _webDriverLocator;
    final private Locator   _parent;
    public Locator (Locator parent, String locatorString)
    {
        this._locatorString     = locatorString;
        this._parent            = parent;
        this._webDriverLocator  = parseLocator(locatorString);
    }
    final public By webDriverLocator () { return this._webDriverLocator; }
    final public boolean hasParent () { return this._parent != null; }
    final public Locator parent () { return this._parent; }
    final public Locator createChild (String childLocatorString)
    {
        return Locator.create(this, childLocatorString);
    }
    @Override
    public String toString() {
        return _parent != null ? "[ " + _parent + " ] -> " + this._locatorString : this._locatorString;
    }
}
