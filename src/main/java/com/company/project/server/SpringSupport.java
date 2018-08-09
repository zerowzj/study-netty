package com.company.project.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Spring Support
 *
 * @author wangzhj
 */
public class SpringSupport implements InitializingBean, DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSupport.class);

    /* Socket服务器 */
    private final SocketServer server;

    public SpringSupport(SocketServer server) {
        super();
        this.server = server;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LOGGER.info("Shutdown callback is invoked.");
                LOGGER.info("shutdown bossGroup and workerGroup");
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        server.stop();
    }
}
