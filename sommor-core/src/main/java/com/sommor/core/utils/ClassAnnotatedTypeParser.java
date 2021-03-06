package com.sommor.core.utils;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class ClassAnnotatedTypeParser {

    private static final Map<Class, Class[]> CLASS_MAP_BY_TARGET_CLASS = new ConcurrentHashMap<>(128);

    public static Class[] parse(Class targetClass) {
        targetClass = resolveTargetClass(targetClass);

        Class[] classes = CLASS_MAP_BY_TARGET_CLASS.get(targetClass);
        if (null == classes) {
            synchronized (CLASS_MAP_BY_TARGET_CLASS) {
                classes = CLASS_MAP_BY_TARGET_CLASS.get(targetClass);
                if (null == classes) {
                    AnnotatedType annotatedType = targetClass.getAnnotatedSuperclass();
                    if (null != annotatedType) {
                        classes = parseEntityClass(annotatedType);
                    }

                    if (null == classes) {
                        AnnotatedType[] annotatedTypes = targetClass.getAnnotatedInterfaces();
                        if (null != annotatedTypes && annotatedTypes.length > 0) {
                            classes = parseEntityClass(annotatedTypes[0]);
                        }
                    }

                    if (null == classes) {
                        classes = new Class[] { Void.class };
                    }

                    CLASS_MAP_BY_TARGET_CLASS.put(targetClass, classes);
                }
            }
        }

        if (classes.length == 1 && classes[0] == Void.class) {
            return null;
        }

        return classes;
    }

    private static Class[] parseEntityClass(AnnotatedType annotatedType) {
        List<Class> classes = new ArrayList<>();

        if (null != annotatedType && annotatedType.getType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) annotatedType.getType();
            Type[] types = parameterizedType.getActualTypeArguments();
            for (Type type : types) {
                if (type instanceof Class) {
                    classes.add((Class) type);
                } else if (type instanceof ParameterizedType) {
                    Type rawType = ((ParameterizedType) type).getRawType();
                    if (rawType instanceof Class) {
                        classes.add((Class) rawType);
                    } else {
                        throw new RuntimeException("unknown rawType: " + rawType.getTypeName());
                    }
                } else {
                    throw new RuntimeException("unknown type: " + type.getTypeName());
                }
            }
        }

        return classes.toArray(new Class[0]);
    }

    private static Class resolveTargetClass(Class targetClass) {
        if (Proxy.isProxyClass(targetClass)) {
            return targetClass.getInterfaces()[0];
        }

        return targetClass;
    }

}
