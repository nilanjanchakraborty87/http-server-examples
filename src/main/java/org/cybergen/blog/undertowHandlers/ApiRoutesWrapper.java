package org.cybergen.blog.undertowHandlers;

import io.undertow.Handlers;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;

/**
 * Class org.cybergen.blog.undertowHandlers.wrapper
 * Created by vishnu667 on 5/9/15.
 */
public class ApiRoutesWrapper implements HandlerWrapper {

    @Override
    public HttpHandler wrap(HttpHandler httpHandler) {
        return Handlers.path()
                .addPrefixPath("/",httpHandler)
                .addPrefixPath("/api",new SimpleUndertowBaseHandler());
    }
}
