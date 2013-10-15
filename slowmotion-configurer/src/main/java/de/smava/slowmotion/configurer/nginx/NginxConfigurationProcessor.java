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
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class NginxConfigurationProcessor extends BaseProcessor implements ConfigurationProcessor {
    private static final Logger logger = LoggerFactory.getLogger(NginxConfigurationProcessor.class);
    private static final String DEST_FILE = "nginx.processor.destination";
    private static final String TEMPLATE_PATH = "nginx.processor.template";
    private static final String HOSTS_PATTERN = "${hosts}";

    @Override
    public void process(Set<String> urls) {
        loadProperties();
        File destination = new File(getProp().get(DEST_FILE).toString());
        if (destination.exists()) {destination.delete();}

        String template;
        try {
            template = FileUtils.readFileToString(new File(getProp().get(TEMPLATE_PATH).toString()));
            StringBuffer buffer = new StringBuffer();
            buffer.append(template.replace(HOSTS_PATTERN,composeHosts(urls)));
            FileUtils.write(destination,buffer.toString());
        } catch (IOException e) {
            logger.error("unable to precess nginx template",e);
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
        for (String link : urls) {
            if (!isLocal(link)) {
                try {
                    result.append(new URL(link).getHost());
                    result.append(" ");
                } catch (MalformedURLException e) {
                    logger.error("cannot process link [" + link + "]",e);
                }
            }
        }
        return result.toString().trim();
    }
}
