package de.smava.slowmotion.configurer;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:16 PM
 *
 * Entry point for slow motion configuration utility that prepares /etc/host file and
 * nginx configuration for testing
 *
 */
public final class Main {

    private Main () {
//      close constructor to ensure that no one is reusing entry point
    }


    public static void main(String[] args) {
        ConfigurationService service = new ConfigurationService();
        service.process();
    }
}
