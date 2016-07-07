package org.cybergen.blog;

import io.undertow.Handlers;

import static io.undertow.Handlers.*;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Headers;

import org.apache.commons.io.IOUtils;
import org.cybergen.blog.undertowHandlers.HelloWorldNioHandler;
import org.cybergen.blog.undertowHandlers.HelloWorldWorkerThreadHandler;
import org.cybergen.blog.undertowHandlers.PostRequestHandler;

import java.io.File;

/**
 * Class org.cybergen.blog.UnderTowExample
 * Created by vishnu667 on 21/8/15.
 * <p/>
 * mvn exec:java -Dexec.mainClass="org.cybergen.blog.UnderTowExample"
 * <p/>
 * ab -k -r -n 1000000 -c 1500 http://localhost:8080/
 */


public class UnderTowExample {

    public static long counter = 0l;

    public static synchronized long func() {
        return counter++;
    }

    public static void main(final String[] args) {

        HttpHandler handler = new HttpHandler() {
            @Override
            public void handleRequest(HttpServerExchange exchange) throws Exception {
                if (exchange.isInIoThread()) {
                    exchange.dispatch();
                }
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send("{\"status\":\"failed\"}");
            }
        };

        ResourceHandler resourceHandler = resource(new FileResourceManager(new File("src/main/resources"), 100)).setDirectoryListingEnabled(true);

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(Handlers.path()
                        .addPrefixPath("/", handler)
                        .addPrefixPath("/testN", new HelloWorldNioHandler())
                        .addPrefixPath("/testW", new HelloWorldWorkerThreadHandler())
                        .addPrefixPath("/post", new PostRequestHandler())
                        .addPrefixPath("/file", resourceHandler)).build();
        server.start();
    }
}
