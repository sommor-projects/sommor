package com.sommor.mybatis.entity.definition;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
public class EntityClassParser {
    private static final Map<Class, Class> ENTITY_CLASS_MAP_BY_TARGET_CLASS = new ConcurrentHashMap<>(128);

    public static Class parse(Class targetClass) {
        targetClass = resolveTargetClass(targetClass);

        Class entityClass = ENTITY_CLASS_MAP_BY_TARGET_CLASS.get(targetClass);
        if (null == entityClass) {
            synchronized (ENTITY_CLASS_MAP_BY_TARGET_CLASS) {
                entityClass = ENTITY_CLASS_MAP_BY_TARGET_CLASS.get(targetClass);
                if (null == entityClass) {
                    AnnotatedType annotatedType = targetClass.getAnnotatedSuperclass();
                    if (null != annotatedType) {
                        entityClass = parseEntityClass(annotatedType);
                    }

                    if (null == entityClass) {
                        AnnotatedType[] annotatedTypes = targetClass.getAnnotatedInterfaces();
                        if (null != annotatedTypes && annotatedTypes.length > 0) {
                            entityClass = parseEntityClass(annotatedTypes[0]);
                        }
                    }

                    if (null == entityClass) {
                        entityClass = Void.class;
                    }

                    ENTITY_CLASS_MAP_BY_TARGET_CLASS.put(targetClass, entityClass);
                }
            }
        }

        return entityClass == Void.class ? null : entityClass;
    }

    private static Class parseEntityClass(AnnotatedType annotatedType) {
        Class entityClass = null;

        if (null != annotatedType && annotatedType.getType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) annotatedType.getType();
            Type[] types = parameterizedType.getActualTypeArguments();
            if (null != types && types.length >= 1 && types[0] instanceof Class) {
                entityClass =  (Class) types[0];
            }
        }

        return entityClass;
    }

    private static Class resolveTargetClass(Class targetClass) {
        if (Proxy.isProxyClass(targetClass)) {
            return targetClass.getInterfaces()[0];
        }

        return targetClass;
    }
}
