package com.sommor.scaffold.spring;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.core.curd.AppLauncher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/26
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static Map<String, Object> beansMap = new ConcurrentHashMap<>(128);

    private static ExtensionExecutor<AppLauncher> executor = ExtensionExecutor.of(AppLauncher.class);

    public static <Bean> Bean getBean(String beanName) {
        Bean bean = (Bean) beansMap.get(beanName);
        if (null == bean) {
            bean = (Bean) applicationContext.getBean(beanName);
            beansMap.put(beanName, bean);
        }

        return bean;
    }

    public static <Bean> Bean getBean(Class<Bean> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
        executor.run(ext -> ext.onLaunched(applicationContext));
    }
}
