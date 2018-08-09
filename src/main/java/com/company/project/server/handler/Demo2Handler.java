package com.company.project.server.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo2Handler extends ChannelHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo2Handler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("+++>channelRead");

//        ctx.fireChannelRead(msg);
    }

}
