package com.sydml.authorization.platform.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 有些情况拿到的beanFactory是null，特别是在应用第三方框架时，dubbo中遇到过，这里不使用
 * DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) getApplicationContext().getParentBeanFactory();
 *
 * @author Liuym
 * @date 2019/4/8 0008
 */
@Component
public class DynamicBeanAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 非是单例模式的
     * 根据obj的类型、创建一个新的bean、添加到Spring容器中
     * 可以有重复的bean，取值的时只能通过obj.getClass().getName()获得Bean而不能通过类型获得Bean
     *
     * @param object
     */
    public void autoPrototypeBean(Object object) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(object.getClass().getName());
        beanFactory.registerBeanDefinition(object.getClass().getName(), beanDefinition);
    }


    /**
     * 单利模式注入bean
     *
     * @param object
     */
    public void autowareSingletonBean(Object object) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        // 1.让obj完成Spring初始化过程中所有增强器检验，只是不重新创建obj
        applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(object, object.getClass().getName());
        // 2.将obj以单例的形式入驻到容器中，此时通过obj.getClass().getName()或obj.getClass()都可以拿到放入Spring容器的Bean
        beanFactory.registerSingleton(object.getClass().getName(), object);
    }

    /**
     * 用于存储Spring容器管理之外的Bean
     */
    public DefaultListableBeanFactory getBeanFactory() {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        return beanFactory;
    }
}
