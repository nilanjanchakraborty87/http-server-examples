package org.cybergen.blog;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.commons.io.IOUtils;
import org.cybergen.blog.undertowHandlers.SimpleUndertowBaseHandler;

import java.nio.ByteBuffer;

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

        HttpHandler handler = new HttpHandler() {
            @Override
            public void handleRequest(HttpServerExchange exchange) throws Exception {
                if(exchange.isInIoThread()) {
                    exchange.dispatch();
                }
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                try {
                    exchange.startBlocking();
                    exchange.getResponseSender().send(ByteBuffer.wrap(IOUtils.toByteArray(exchange.getInputStream())));
                } catch (Exception e) {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                    exchange.getResponseSender().send("{\"status\":\"failed\",\"comment\":\""+e.getLocalizedMessage()+"\"}");
                }


            }
        };

        Undertow server = Undertow.builder()
                .addHttpListener(8088, "localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/", handler)
                        .addPrefixPath("/api", new SimpleUndertowBaseHandler())).build();
        server.start();
    }
}