slow_motion
============

Project to test web pages loading with implication of slow/blocking response times from external resources

2 Modules:

* Configurer - module to generate hosts and http server configurations based on network utilization report
currently HAR report from chromium
* Server - actual server based on jetty that produces responses to any incoming requests only after timeout
value is reached.

_SSL Support:_

ssl is enabled by default on port 9443 of slow server, additionally nginx configuration file is generated
with ssl in mind. In general nginx certificate has to be added to OS trusted layer in order to make module working
"out of the box".

    sudo cp slowmotion-server/src/main/resources/WEB-INF/server.crt /etc/ssl/certs/
    openssl x509 -noout -hash -in /etc/ssl/certs/server.crt

There are few exceptions to that rule:

* firefox browser
    Firefox is using custom storage for trusted certificates


_Suggested usage_
* grab HAR report from Chrome\Chromium
* put HAR report into

    slow_motion/slowmotion-configurer/src/main/resources

* adjust values to desired in

    /configurer.properties

* run de.smava.slowmotion.configurer.Main
* replace /etc/hosts with generated file
* link generated /slowmotion.nginx to your nginx sites-available and sites-enabled folders

_TODO:_
* debian or rpm packaging
* proper usage instructions with usecase scenarios
* command line configuration support
* external configuration file support
* automated ssl certificate import to most of the browsers
