package org.cybergen.blog.undertowHandlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.commons.io.IOUtils;

/**
 * Class org.cybergen.blog.undertowHandlers.PostRequestHandler
 * Created by vishnu667 on 7/7/15.
 */
public class PostRequestHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if(exchange.isInIoThread()){
            exchange.dispatch(this);
            return;
        }

        try {
                exchange.startBlocking();
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");

                String a = new String(IOUtils.toByteArray(exchange.getInputStream()));
                exchange.getResponseSender().send(a);
        } catch (Exception e) {
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send("{\"status\":\"failed\",\"comment\":\"" + e.getLocalizedMessage() + "\"}");
        }

    }
}
