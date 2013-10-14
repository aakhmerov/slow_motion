package de.smava.slowmotion.configurer;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:20 PM
 *
 * Basic processor descriptors
 */
public interface ConfigurationProcessor {
    void process(Set<String> urls);

}
