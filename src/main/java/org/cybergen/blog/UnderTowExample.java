package org.cybergen.blog;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.cybergen.blog.undertowHandlers.ApiRoutesWrapper;
import org.cybergen.blog.undertowHandlers.SimpleUndertowBaseHandler;

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

        HttpHandler handler = new ApiRoutesWrapper().wrap(new HttpHandler() {
            @Override
            public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                httpServerExchange.getResponseSender().send("Base Route");
            }
        });

        HttpHandler handlers = Handlers.path().addPrefixPath("/",new SimpleUndertowBaseHandler());

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(handlers).build();
        server.start();
    }
}