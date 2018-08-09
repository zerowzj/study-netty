package com.company.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动
 *
 * @author wangzhj
 */
public class Startup {

    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    private static String[] FILES = {"spring/spring-*.xml"};

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(FILES);
        context.start();

        //死锁等待
        synchronized (Startup.class) {
            while (true) {
                try {
                    Startup.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }
}
