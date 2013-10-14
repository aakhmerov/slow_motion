package de.smava.slowmotion.configurer.har;

import org.junit.Test;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class HarReportParserTest {

    private static final String HAR_FILE = "localhost.har";
    private static final String TEST_URL = "https://localhost:8443/";
    private static final String TEST_URL2 = "https://localhost:8443/Bilder/Homepage/mobile/hochzeit_smava_JPG.jpg";
    private HarReportParser parser = new HarReportParser();

    @Test
    public void testParse() throws Exception {
        File report = new File(this.getClass().getClassLoader().getResource(HAR_FILE).toURI());
        Set<String> urls = parser.parse(report);
        assertThat(urls,is(notNullValue()));
        assertThat(urls.size(),is(greaterThan(0)));
        assertThat(urls.contains(TEST_URL),is(true));
        assertThat(urls.contains(TEST_URL2),is(true));
    }
}
