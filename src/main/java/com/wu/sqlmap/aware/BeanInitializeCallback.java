package com.wu.sqlmap.aware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
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
public class BeanInitializeCallback implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean{
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("BeanInitializeCallback[setBeanClassLoader] classLoader:{}",classLoader);
    }

    @Override
    public void setBeanName(String name) {
        log.info("BeanInitializeCallback[setBeanName],name:{}",name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("BeanInitializeCallback[setBeanFactory],beanFactory:{}",beanFactory);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("BeanInitializeCallback[afterPropertiesSet]");
    }
}
