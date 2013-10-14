slow_motion
============

Project to test web pages loading with implication of slow/blocking response times from external resources

2 Modules:

* Configurer - module to generate hosts and http server configurations based on network utilization report
currently HAR report from chromium
* Server - actual server based on jetty that produces responses to any incoming requests only after timeout
value is reached.
