package my.test.app.utils.base;

import java.io.File;

public class Configuration extends AQAConfig {

    final public EnVar SITE_URL = this.enVar("site_url", true);

    @Override
    protected File configFile() {
        String filePath = ".env/" + System.getProperty("env") +  ".properties";
        return new File(filePath);
    }

    public Configuration() {
    }

    static private Configuration _instance;

    static public Configuration get (){
        if(_instance == null)
            _instance = new Configuration();
        return _instance;
    }
}
