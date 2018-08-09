package com.company.project.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Socket初始化器
 *
 * @author wangzhj
 */
public class SocketInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketInitializer.class);

    private static final String SOCKET_HANDLER = "socket_handler";
    private static final String OCKET_HANDLER = "socket_handler";
    private static final String CKET_HANDLER = "socket_handler";
    private static final String KET_HANDLER = "socket_handler";

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        LOGGER.info("initChannel");
        ChannelPipeline pipeline = channel.pipeline();
        addSocketHandlers(pipeline);
    }

    protected void addSocketHandlers(ChannelPipeline pipeline) {

        pipeline.addLast("demo1", new Demo1Handler());
        pipeline.addLast("demo2", new Demo2Handler());
        pipeline.addLast("demo3", new Demo3Handler());

//        pipeline.addLast(new StringEncoder(Charset.forName("GBK")));
//        pipeline.addLast(SOCKET_HANDLER, new SocketHandler());
//        pipeline.addChannelHandlerContextLast(new ByteArrayEncoder());
    }
}
