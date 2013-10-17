package de.smava.slowmotion.configurer.linux;

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
 *
 * Copy localhost /etc/hosts file content and append it with hosts that don't match
 * localhost signature with reference to loopback address
 */
public class HostsConfigurationProcessor extends BaseProcessor implements ConfigurationProcessor  {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostsConfigurationProcessor.class);
    private static final String DEST_FILE = "unix.processor.destination";
    private static final String HOSTS_PATH = "/etc/hosts";
    private static final String LOOPBACK = "127.0.0.1";


    @Override
    public void process(Set<String> urls) {

        loadProperties();
        File destination = new File(getProp().get(DEST_FILE).toString());
        if (destination.exists()) {destination.delete();}
        try {
            String existing = FileUtils.readFileToString(new File(HOSTS_PATH));
            StringBuffer buffer = new StringBuffer();
            buffer.append(existing);
            buffer.append("\n");
            buffer.append("# slow motion testing section");
            buffer.append("\n");
            List<String> added = new ArrayList<String>();
            for (String url : urls) {
                if (!isLocal(url)) {
                    String line = loopbackLine(url);
                    if (!added.contains(line)) {
                        added.add(line);
                        buffer.append(line);
                        buffer.append("\n");
                    }
                }
            }
            FileUtils.write(destination,buffer.toString());
        } catch (IOException e) {
            LOGGER.error("can't process hosts file", e);
        }

    }

    /**
     * Compose loopback address hosts line for the host provided in URL.
     *
     * @param url
     * @return
     */
    private String loopbackLine(String url) {
        String result = "";
        try {
            URL wrapped = new URL(url);
            result = LOOPBACK + "\t" + wrapped.getHost();
        } catch (MalformedURLException e) {
            LOGGER.error("cant compose loopback line for url [" + url + "]", e);
        }
        return result;
    }


}
