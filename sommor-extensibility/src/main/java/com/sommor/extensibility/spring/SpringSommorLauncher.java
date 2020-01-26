package com.sommor.extensibility.spring;

import com.sommor.extensibility.ExtensionManager;
import com.sommor.extensibility.Implementor;
import com.sommor.extensibility.Plugin;
import com.sommor.extensibility.PluginManager;
import com.sommor.extensibility.config.Implement;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Component
public class SpringSommorLauncher implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Implement implement = bean.getClass().getAnnotation(Implement.class);
        if (null != implement) {
            Plugin plugin = PluginManager.getInstance().match(bean.getClass());
            //if (null != plugin) {
            System.out.println("implementor: " + bean.getClass().getName());
                Implementor<?> implementor = new Implementor<>(plugin, bean, implement.priority());
                ExtensionManager.getInstance().registerImplementor(implementor);
            //}
        }

        return bean;
    }
}
