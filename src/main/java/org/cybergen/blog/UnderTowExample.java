package org.cybergen.blog;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Class org.cybergen.blog.UnderTowExample
 * Created by vishnu667 on 21/8/15.
 *
 * ab -k -r -n 1000000 -c 1500 http://localhost:8080/
 *
 */


public class UnderTowExample {

    public static long counter = 0l;

    public static synchronized long func(){
        return counter++;
    }

    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World "+func());
                    }
                }).build();
        server.start();
    }
}