package com.company.project.support;

import com.company.project.support.blogic.BLogic;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务逻辑工厂
 *
 * @author wangzhj
 */
public class BLogicFactory implements BeanPostProcessor {

    private static Map<String, BLogic> BLOGIC_MAP = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(Cmd.class) && bean instanceof BLogic) {
            Cmd cmd = clazz.getAnnotation(Cmd.class);
            BLOGIC_MAP.put(cmd.value(), (BLogic) bean);
        }
        return bean;
    }

    /**
     * 获取BLogic
     *
     * @param cmd
     * @return BLogic
     */
    public static BLogic get(String cmd) {
        return BLOGIC_MAP.get(cmd);
    }
}
