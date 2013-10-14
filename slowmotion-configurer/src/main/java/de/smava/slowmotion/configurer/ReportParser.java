package de.smava.slowmotion.configurer;

import java.io.File;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:22 PM
 *
 * Basic parser contract to fetch list of URLs accessed by a web page loading process
 * based on underlying report format.
 *
 */
public interface ReportParser {

    /**
     * Parse specified file and return list urls accessed by page.
     * @param reportFile - wrapped report file
     * @return list of urls accessed, based on report
     */
    public Set<String> parse (File reportFile);
}
