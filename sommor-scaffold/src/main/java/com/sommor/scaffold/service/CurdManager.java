package com.sommor.scaffold.service;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityClassParser;
import com.sommor.mybatis.repository.CurdRepository;
import com.sommor.scaffold.utils.ClassAnnotatedTypeParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Component
public class CurdManager implements BeanPostProcessor {

    private static Map<Class, CurdRepository> entityRepositoryMap = new HashMap<>();

    private static Map<Class, CurdService> entityServiceMap = new HashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CurdRepository) {
            entityRepositoryMap.put(parseEntityClass(bean, beanName), (CurdRepository) bean);
        } else if (bean instanceof CurdService) {
            entityServiceMap.put(parseEntityClass(bean, beanName), (CurdService) bean);
        }
        return bean;
    }

    private Class parseEntityClass(Object bean, String beanName) {
        Class[] classes = ClassAnnotatedTypeParser.parse(bean.getClass());
        if (null == classes || classes.length == 0) {
            throw new RuntimeException("unknown entity class for bean: " + beanName);
        }
        return classes[0];
    }

    public <Entity extends BaseEntity> CurdRepository<Entity> getCurdRepository(Class<Entity> entityClass) {
        return entityRepositoryMap.get(entityClass);
    }

    public <Entity extends BaseEntity> CurdService getCurdService(Class<Entity> entityClass) {
        return entityServiceMap.get(entityClass);
    }
}
