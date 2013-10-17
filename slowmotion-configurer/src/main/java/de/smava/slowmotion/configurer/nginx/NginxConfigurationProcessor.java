package de.smava.slowmotion.configurer.nginx;

import de.smava.slowmotion.configurer.BaseProcessor;
import de.smava.slowmotion.configurer.ConfigurationProcessor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class NginxConfigurationProcessor extends BaseProcessor implements ConfigurationProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(NginxConfigurationProcessor.class);
    private static final String DEST_FILE = "nginx.processor.destination";
    private static final String TEMPLATE_PATH = "nginx.processor.template";
    private static final String HOSTS_PATTERN = "nginx.processor.pattern";


    /**
     * Process urls to compose nginx configuration.
     * following properties are expected to be set in configuration file:
     *
     * nginx.processor.destination - destination file that will be written with processed nginx configuration
     * nginx.processor.template - absolute path to the nginx configuration template file
     * nginx.processor.pattern - pattern that will be replaced in template file with composed list of hosts
     *
     *
     * @param urls
     */
    @Override
    public void process(Set<String> urls) {
        loadProperties();
        File destination = new File(getProp().get(DEST_FILE).toString());
        if (destination.exists()) {destination.delete();}

        String template;
        try {
            template = FileUtils.readFileToString(new File(getProp().get(TEMPLATE_PATH).toString()));
            StringBuffer buffer = new StringBuffer();
            buffer.append(template.replace(getProp().get(HOSTS_PATTERN).toString(),composeHosts(urls)));
            FileUtils.write(destination,buffer.toString());
        } catch (IOException e) {
            LOGGER.error("unable to precess nginx template", e);
        }

    }

    /**
     * Compose list of hosts for nginx configuration out of list of URLs provided
     * by report file
     *
     * @param urls
     * @return
     */
    private String composeHosts(Set<String> urls) {
        StringBuilder result = new StringBuilder();
        List<String> add = new ArrayList<String>();
        for (String link : urls) {
            if (!isLocal(link) ) {
                try {
                    String host = new URL(link).getHost();
                    if (!add.contains(host)) {
                        add.add(host);
                        result.append(host);
                        result.append(" ");
                    }
                } catch (MalformedURLException e) {
                    LOGGER.error("cannot process link [" + link + "]", e);
                }
            }
        }
        return result.toString().trim();
    }
}
