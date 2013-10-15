package de.smava.slowmotion.configurer.nginx;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class NginxConfigurationProcessorTest {

    private static final String OUT = "/home/aakhmerov/Work/smava/dev/slow_motion/slowmotion-configurer/src/test/resources/slowmotion.nginx";
    private NginxConfigurationProcessor processor = new NginxConfigurationProcessor();

    @Test
    public void testParse() throws Exception {
        Set<String> lines = new HashSet<String>();
        String test1 = "http://127.0.0.1:8443/t1";
        String test2 = "https://localhost:8443/t1";
        String test3 = "https://test.de/t1213?6=1";
        String test4 = "http://127.1.1.1/t1213?6=1";
        String result = "test.de 127.1.1.1";

        lines.add(test1);
        lines.add(test2);
        lines.add(test3);
        lines.add(test4);
        processor.process(lines);
        String prepared = FileUtils.readFileToString(new File(OUT));
        assertThat(prepared,is(notNullValue()));
        assertThat(prepared.contains(result),is(true));
    }
}
