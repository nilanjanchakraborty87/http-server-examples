package org.cybergen.blog.handlers;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by vishnu on 20/11/14.
 */
public class MainInitializer extends ChannelInitializer<SocketChannel>{

    public MainInitializer() {

    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new PingPongHandler());
    }
}
