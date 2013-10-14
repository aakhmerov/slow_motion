package de.smava.slowmotion.configurer.har;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smava.slowmotion.configurer.ReportParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:21 PM
 *
 * Parser of HAR report produced by chromium on web page networking, or
 * any other tool producing HAR reports and fetch list of URLs accessed
 * by browser.
 *
 */
public class HarReportParser implements ReportParser {
    private static final Logger logger = LoggerFactory.getLogger(HarReportParser.class);
    private static final String ENTRIES_KEY = "entries";
    private static final String REQUESTS_KEY = "request";
    private static final String URL_KEY = "url";
    private static final String LOG_KEY = "log";

    private Set<String> urls;


    /**
     * Iterate through specified HAR file using Jackson implementation and
     * fetch all urls according to the format, while they are present.
     *
     * if file does not exist or is empty - empty hash set will be returned as result
     * of the implementation.
     *
     * @param harFile - wrapped reference to HAR report file
     * @return
     */
    public Set<String> parse (File harFile) {
        urls = new HashSet<String>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String,Object> values = mapper.readValue(harFile, Map.class);
            List <Map<String,Object>> externals = (List<Map<String, Object>>) ((Map<String, Object>) values.get(LOG_KEY)).get(ENTRIES_KEY);
            for (Map<String,Object> resource : externals) {
                Map<String,Object> request = (Map<String, Object>) resource.get(REQUESTS_KEY);
                urls.add(request.get(URL_KEY).toString());
            }
        } catch (IOException e) {
            logger.error("unable to parse HAR file",e);
        }
        return urls;
    }
}
