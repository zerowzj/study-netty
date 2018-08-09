package com.company.project.server.handler;

import com.company.project.support.BLogicFactory;
import com.company.project.support.blogic.BLogic;
import com.company.project.support.context.RequestContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Socket Channel处理器
 *
 * @author wangzhj
 */
public class SocketHandler extends ChannelHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("===>channelRegistered");
    }

    /**
     * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。
     * 也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("===>channelActive");
    }


    /**
     * 读取服务器发送过来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("===>channelRead");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String message = new String(req, "UTF-8");
        LOGGER.info("Netty-Server:Receive Message," + message);
        try {
            //
            RequestContext cxt = new RequestContext();
            //
            BLogic bLogic = BLogicFactory.get(message);
            bLogic.doBusiness(cxt);
        } catch (Exception ex) {
            throw ex;
        }
//        buf.release();
        //通知执行下一个
        ctx.fireChannelRead(msg);
    }

    /**
     * 读取完毕客户端发送过来的数据之后的操作
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.channel().writeAndFlush("bbbbbbbbbbbbbb");
        LOGGER.info("===>channelReadComplete");
//        ctx.channel().close();
    }

    /**
     * 当客户端主动断开服务端的链接后，这个通道就是不活跃的。
     * 也就是说客户端与服务端的关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("===>channelInactive");
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("===>channelUnregistered");
    }

    /**
     * 服务端发生异常的操作
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("===>exceptionCaught");
        cause.printStackTrace();
        ctx.channel().writeAndFlush("ssssssfffffffffffff");
        ctx.close();
    }
}
