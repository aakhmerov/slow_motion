package de.smava.slowmotion.configurer;

import de.smava.slowmotion.configurer.har.HarReportParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:24 PM
 *
 * Business service implementation to handle preparations of configuration files based
 * on networking report.
 */
public class ConfigurationService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    private static final String PROCESSOR_SIGNATURE = ".*processor\\.class.*";
    private static final String PROPERTIES_FILE = "configurer.properties";
    private static final String HAR_FILE = "har.file";
    private static final String PARSER = "report.parser";
    private ReportParser parser;
    private List<ConfigurationProcessor> processors = new ArrayList<ConfigurationProcessor>();
    private Properties prop = new Properties();


    /**
     * initialize processors that generate actual configuration files. Implementation based on
     * values read from the properties files.
     */
    public void init() {
        if (prop.size() == 0) {
            try {
                prop.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            } catch (IOException e) {
                logger.error("cant load properties",e);
            }
        }
        if (processors.size() == 0) {
            for (Map.Entry<Object, Object> element : prop.entrySet()) {
                if (element.getKey().toString().matches(PROCESSOR_SIGNATURE)) {
                    try {
                        this.processors.add((ConfigurationProcessor) Class.forName(element.getValue().toString()).newInstance());
                    } catch (InstantiationException e) {
                        logger.error("Cant load processor",e);
                    } catch (IllegalAccessException e) {
                        logger.error("Cant load processor", e);
                    } catch (ClassNotFoundException e) {
                        logger.error("Cant load processor", e);
                    }
                }
            }
        }
        if (parser == null) {
            try {
                parser = (ReportParser) Class.forName(prop.get(PARSER).toString()).newInstance();
            } catch (InstantiationException e) {
                logger.error("Cant load parser", e);
            } catch (IllegalAccessException e) {
                logger.error("Cant load parser", e);
            } catch (ClassNotFoundException e) {
                logger.error("Cant load parser", e);
            }
        }
    }

    /**
     * Processing method to execute business logic based on configuration
     * file values, not on values provided through method calls.
     *
     * Read configured values and invoke method with input parameters
     */
    public void process() {
        init();
        File har = new File(prop.get(HAR_FILE).toString());
        this.process(har);
    }



    /**
     * Parse specified report file and produce 2 outcome files based on it's values.
     * Parser is instantiated as HAR report parser.
     * All configured processors get urls from analysis files after that and perform whatever
     * they have to.
     *
     * @param report
     */
    public void process (File report) {
        init();
        Set<String> urls = parser.parse(report);
        for (ConfigurationProcessor processor : processors) {
            processor.process(urls);
        }
    }


    public ReportParser getParser() {
        return parser;
    }

    public void setParser(ReportParser parser) {
        this.parser = parser;
    }

    public List<ConfigurationProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(List<ConfigurationProcessor> processors) {
        this.processors = processors;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
}
