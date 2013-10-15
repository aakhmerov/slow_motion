package de.smava.slowmotion.configurer;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationServiceTest {

    private static final int NUMBER_OF_MAIN_PROPERTIES = 3;
    private ConfigurationService toTest = new ConfigurationService();

    @Test
    public void testInitialization() throws Exception {
        toTest.initProcessors();
        assertThat(toTest.getProp(), is(notNullValue()));
        assertThat(toTest.getProp().size(), is(greaterThanOrEqualTo(NUMBER_OF_MAIN_PROPERTIES)));
        assertThat(toTest.getProcessors(), is(notNullValue()));
        assertThat(toTest.getProcessors().size(),is(2));
    }


    /**
     * Just validate that exceptions are not thrown
     * @throws Exception
     */
    @Test
    public void testProcess() throws Exception {
        toTest.process();
        toTest.process(new File("test"));
    }
}
