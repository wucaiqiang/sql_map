package com.wu.sqlmap.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * description:TODO
 *
 * @author caiqiang.wu
 * @create 2020/03/13
 **/
@Slf4j
@Component
public class BeanPostProcessCallback implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("BeanPostProcessCallback[postProcessBeforeInitialization],beanName:{},bean:{}", beanName, bean);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("BeanPostProcessCallback[postProcessAfterInitialization],beanName:{},bean:{}", beanName, bean);
        return bean;
    }
}
