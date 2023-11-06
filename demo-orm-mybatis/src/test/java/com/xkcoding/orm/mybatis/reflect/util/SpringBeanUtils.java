package com.xkcoding.orm.mybatis.reflect.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtils<T> implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static <T> T  getBean(Class<T> clazz) {
        return applicationContext != null?applicationContext.getBean(clazz):null;
    }

    public static Object getBeanByString(String beanName) throws BeansException {
        return applicationContext.getBean(beanName);
    }
}
