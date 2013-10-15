package de.smava.slowmotion.server;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;
import org.mortbay.thread.ThreadPool;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: aakhmerov
 * Date: 10/14/13
 * Time: 2:17 PM
 *
 * Jetty based server implementation launcher class that replies with slow or hanging
 * responses.
 */

public final class Main {

    private static final String PASSWORD = "changeme";
    private static final String KEYSTORE = "WEB-INF/keystore";

    /**
     * Hidden constructor, just to indicate that no one should instantiate entry point
     */
    private Main() {
    }

    private static final int PORT = 9001;
    private static final String WEB_XML =
            "WEB-INF/web.xml";

    public static void main(String[] args) throws Exception {
        Server server;
        server = new Server();
        server.setThreadPool(createThreadPool());
        server.addConnector(createConnector());
        server.addConnector(createSSLConnector());
        server.setHandler(createHandlers());
        server.setStopAtShutdown(true);
        server.start();
    }

    private static Connector createSSLConnector() {
        SslSocketConnector ssl = new SslSocketConnector();
        ssl.setPort(9443);
        ssl.setHost("localhost");
        ssl.setKeyPassword(PASSWORD);
        ssl.setKeystore(getResource(KEYSTORE).toString());
        return ssl;
    }

    private static ThreadPool createThreadPool() {
        // TODO: You should configure these appropriately
        // for your environment - this is an example only
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMinThreads(10);
        threadPool.setMaxThreads(100);
        return threadPool;
    }

    private static SelectChannelConnector createConnector() {
        SelectChannelConnector connector =
                new SelectChannelConnector();
        connector.setPort(PORT);
        connector.setHost("localhost");
        return connector;
    }

    private static HandlerCollection createHandlers() {
        WebAppContext ctx = new WebAppContext();
        ctx.setContextPath("/");
        ctx.setWar(getShadedWarUrl());
        List<Handler> handlers = new ArrayList<Handler>();

        handlers.add(ctx);

        HandlerList contexts = new HandlerList();
        contexts.setHandlers(handlers.toArray(new Handler[0]));

        RequestLogHandler log = new RequestLogHandler();

        HandlerCollection result = new HandlerCollection();
        result.setHandlers(new Handler[]{contexts, log});

        return result;
    }

    private static String getShadedWarUrl() {
        String urlStr = getResource(WEB_XML).toString();
        // Strip off "WEB-INF/web.xml"
        return urlStr.substring(0, urlStr.length() - 15);
    }

    private static URL getResource(String aResource) {
        return Thread.currentThread().
                getContextClassLoader().getResource(aResource);
    }
}