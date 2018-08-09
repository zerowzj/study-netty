package com.company.project.server;

import com.company.project.server.config.Configuration;
import com.company.project.server.config.SocketConfig;
import com.company.project.server.handler.SocketInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Socket服务器
 *
 * @author wangzhj
 */
public class SocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    /* 连接处理（处理接收进来的连接）通道accept*/
    private EventLoopGroup bossGroup;
    /* 事件处理（处理已经被接收的连接）通道读写事件*/
    private EventLoopGroup workerGroup;

    /* 服务器配置 */
    private Configuration config;
    /* Socket初始化器 */
    private SocketInitializer pipelineFactory = new SocketInitializer();

    public SocketServer(Configuration config) {
        this.config = config;
    }

    /**
     * 启动Server
     */
    public void start() throws Exception {
        startAsync().syncUninterruptibly();
    }

    public Future<Void> startAsync() throws Exception {
        //
        initGroups();
        //
        Class<? extends ServerChannel> channelClass = NioServerSocketChannel.class;
        //引导
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(channelClass)          //指定使用的channel
                .childHandler(pipelineFactory); //绑定客户端连接时候触发操作
        //
        applyConnectionOptions(bootstrap);
        //地址
        String hostName = config.getHostName();
        int port = config.getPort();
        InetSocketAddress address = new InetSocketAddress(port);
        if (hostName != null) {
            address = new InetSocketAddress(hostName, port);
        }
        //
        ChannelFuture channelFuture = bootstrap.bind(address)
                .addListener((Future<Void> future) -> {
                    if (future.isSuccess()) {
                        LOGGER.info("----------------------------------------");
                        LOGGER.info("Socket server started at port: {}", port);
                        LOGGER.info("----------------------------------------");
                    } else {
                        LOGGER.error("Socket server start failed at port: {}!", port);
                    }
                });
        return channelFuture;
    }

    protected void initGroups() {
        bossGroup = new NioEventLoopGroup(config.getBossThreads());
        workerGroup = new NioEventLoopGroup(config.getWorkerThreads());
    }

    protected void applyConnectionOptions(ServerBootstrap bootstrap) {
        SocketConfig config = this.config.getSocketConfig();
        bootstrap.childOption(ChannelOption.TCP_NODELAY, config.isTcpNoDelay());
        if (config.getTcpSendBufferSize() != -1) {
            bootstrap.childOption(ChannelOption.SO_SNDBUF, config.getTcpSendBufferSize());
        }
        if (config.getTcpReceiveBufferSize() != -1) {
            bootstrap.childOption(ChannelOption.SO_RCVBUF, config.getTcpReceiveBufferSize());
            bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(config.getTcpReceiveBufferSize()));
        }
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, config.isTcpKeepAlive());
        bootstrap.childOption(ChannelOption.SO_LINGER, config.getSoLinger());

        bootstrap.option(ChannelOption.SO_REUSEADDR, config.isReuseAddress());
        bootstrap.option(ChannelOption.SO_BACKLOG, config.getAcceptBackLog());
    }


    /**
     * 停止Server
     * 优雅退出，释放线程池资源
     * 调用NIO线程组的shutdownGracefully进行优雅退出，它会释放跟shutdownGracefully相关联的资源。
     */
    public void stop() throws Exception {
        bossGroup.shutdownGracefully().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();

        LOGGER.info("Socket server stopped");
    }
}
