package de.smava.slowmotion.configurer.linux;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnixConfigurationProcessorTest {

    private static final String HOSTS = "/etc/hosts";
    private static final String OUT = "/home/aakhmerov/Work/smava/dev/slow_motion/slowmotion-configurer/src/test/resources/hosts";
    private HostsConfigurationProcessor processor = new HostsConfigurationProcessor();

    @Test
    public void testProcess() throws Exception {
        Set<String> lines = new HashSet<String>();
        String test1 = "http://127.0.0.1:8443/t1";
        String test2 = "https://localhost:8443/t1";
        String test3 = "https://test.de/t1213?6=1";
        String test4 = "http://127.1.1.1/t1213?6=1";
        String result = "127.0.0.1\ttest.de";
        String result2 = "127.0.0.1\t127.1.1.1";
        lines.add(test1);
        lines.add(test2);
        lines.add(test3);
        lines.add(test4);
        processor.process(lines);
        List<String> initial = FileUtils.readLines(new File(HOSTS));
        List<String> prepared = FileUtils.readLines(new File(OUT));

        assertThat(prepared.size(), is(greaterThan(initial.size())));
        assertThat(prepared.contains(result), is(true));
        assertThat(prepared.contains(result2), is(true));
    }
}
