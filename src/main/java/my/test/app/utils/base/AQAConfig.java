package my.test.app.utils.base;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.*;

abstract public class AQAConfig {

    final private Logger _log = Logger.getLogger(this.getClass());
    final private File _configFile;
    final private List<File> _sysConfigRewrite = new ArrayList<>();
    final private Map<String, EnVar> _envVarMap = new HashMap<>();
    private Properties _properties;
    private boolean _initialized = false;

    public AQAConfig() {
        this._configFile = this.configFile();
        _log.info("Create config instance of \"" + this.getClass().getSimpleName() + "\": " + this._configFile);
        _log.debug("-------------------------------------------");
        _log.debug("---   Register environment variables   ---");
    }

    final public void addSysConfigRewrite(File sysConfig) {
        this._sysConfigRewrite.add(sysConfig);
    }

    private void _initSysConfig() {
        if (!this._initialized && this._sysConfigRewrite.size() > 0) {
            _log.debug("-------------------------------------------");
            _log.debug("--- Read properties from system configs ---");
            for (File cfg : this._sysConfigRewrite) {
                _log.debug("Read and set system properties from: " + cfg);
                Properties props = new Properties();
                try (FileInputStream input = new FileInputStream(cfg)) {
                    props.load(input);
                } catch (IOException e) {
                    _log.error("Unable to load configuration: " + cfg, e);
                    throw new RuntimeException(e);
                }
                System.getProperties().putAll(props);
                this._initialized = true;
            }
            _log.debug("-------------------------------------------");
        }
    }

    private Properties _props() {
        if (this._properties == null) {
            _log.debug("-------------------------------------------");
            this._initSysConfig();
            _log.debug("Read main properties from: " + this._configFile);
            this._properties = new Properties();
            try (FileInputStream input = new FileInputStream(this._configFile)) {
                this._properties.load(input);
            } catch (IOException e) {
                _log.error("Unable to load configuration: " + this._configFile, e);
                throw new RuntimeException(e);
            }
        }
        return this._properties;
    }

    protected String get(String name) {
        Object value = this._props().get(name);
        if (value != null)
            return value.toString().trim().replaceAll("^([\"'])(.*?)([\"'])$", "$2");

        return null;
    }

    final protected EnVar enVar(String name) {
        return this.enVar(name, false, true);
    }

    final protected EnVar enVar(String name, boolean system) {
        return this.enVar(name, system, true);
    }

    final protected EnVar enVar(String name, boolean system, boolean required) {
        String attrs = system ? "sys" : "";
        attrs += required ? ",req" : "";
        _log.debug("Register environment variable: " + name + " [" + attrs + "]");
        for (String varMapName : this._envVarMap.keySet()) {
            if (varMapName.equalsIgnoreCase(name))
                throw new RuntimeException("Duplicates of variable name are not allowed: \" " + name + "\"");
        }
        EnVar enVar = new EnVar(name, system, required);
        this._envVarMap.put(name, enVar);
        return enVar;
    }

    abstract protected File configFile();

    final public class EnVar {
        final public String varName;
        final public boolean systemEnvironment;
        final public Value value;
        private boolean _required;
        private String _defaultValue;

        EnVar(String name, boolean system, boolean required) {
            this.varName = name;
            this.systemEnvironment = system;
            this._required = required;
            this.value = new Value();
        }

        final public EnVar setDefault(String value) {
            this._defaultValue = value;
            return this;
        }

        final public String defaultValue() {
            return this._defaultValue;
        }

        final public EnVar setRequired(boolean value) {
            this._required = value;
            return this;
        }

        final public boolean isRequired() {
            return this._required;
        }

        final public String asString() {
            return this.value._get();
        }

        final public int asInt() {
            try {
                return Integer.parseInt(this.value._get());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Config parameter can not be converted to integer: " +
                        EnVar.this.varName + " = " + this.value._get(), e);
            }
        }

        final public Float asFloat() {
            try {
                return Float.parseFloat(this.value._get());
            } catch (NumberFormatException e) {
                throw new RuntimeException("Config parameter can not be converted to integer: " +
                        EnVar.this.varName + " = " + this.value._get(), e);
            }
        }

        final public boolean asBoolean() {
            return this.value._get().equalsIgnoreCase("true");
        }

        @Override
        final public String toString() {
            return this.value._get();
        }

        final public class Value {

            private String _strValue;

            private String _get() {
                if (this._strValue == null) {
                    AQAConfig.this._initSysConfig();
                    // Try to set value from system environment
                    if (EnVar.this.systemEnvironment) {
                        this._strValue = System.getenv(EnVar.this.varName);
                        if (this._strValue == null) {
                            this._strValue = System.getProperty(EnVar.this.varName);
                        }
                        if (this._strValue != null)
                            this._strValue = this._strValue.trim().replaceAll("^([\"'])(.*?)([\"'])$", "$2");
                    }
                    if (this._strValue == null) {
                        // Try to set config value
                        this._strValue = AQAConfig.this.get(EnVar.this.varName);
                        if (this._strValue == null) {
                            // Try to set default value
                            this._strValue = EnVar.this._defaultValue;
                            if (EnVar.this._required && this._strValue == null)
                                throw new RuntimeException(
                                        "Required parameter \"" +
                                                EnVar.this.varName + "\" is not defined"
                                );
                        }
                    }
                }
                return this._strValue;
            }

            @Override
            final public String toString() {
                return this._get();
            }
        }
    }
}
