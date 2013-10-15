package de.smava.slowmotion.configurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/15/13
 * Time: 10:42 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseProcessor implements ConfigurationProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BaseProcessor.class);

    private static final String PROPERTIES_FILE = "configurer.properties";
    private static final String LOCAL_INDICATOR = "local";
    private Properties prop = new Properties();

    /**
     * Determine if URL is pointing to a local resource. All urls are considered to be local
     * by default.
     *
     * i.e.  if url is invalid it will be considered to be local so that it is not participating in further
     * processing
     *
     * @param url
     * @return
     */
    public boolean isLocal(String url) {
        boolean result = true;
        try {
            URL wrapped = new URL(url);
            Object matched  = prop.get(wrapped.getHost());
            if (matched == null || !matched.equals(LOCAL_INDICATOR)) {
                result = false;
            }
        } catch (MalformedURLException e) {
            logger.error("cant parse url",e);
        }
        return result;
    }


    /**
     * basic method to load properties with local configurations
     */
    protected void loadProperties () {
        if (prop.size() == 0) {
            try {
                prop.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            } catch (IOException e) {
                logger.error("cant load properties",e);
            }
        }
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
}
