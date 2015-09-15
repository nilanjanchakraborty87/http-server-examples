package org.cybergen.blog.undertowHandlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * Class org.cybergen.blog.undertowHandlers.SimpleUndertowBaseHandler
 * Created by vishnu667 on 5/9/15.
 */
public class SimpleUndertowBaseHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
        httpServerExchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        httpServerExchange.getResponseSender().send("SimpleUnderTowBAseHandler");
    }
}
