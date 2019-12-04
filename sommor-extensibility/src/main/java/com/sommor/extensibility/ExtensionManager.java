package com.sommor.extensibility;

import com.sommor.extensibility.config.Extension;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/23
 */
public class ExtensionManager {

    private static ExtensionManager instance = new ExtensionManager();

    public static ExtensionManager getInstance() {
        return instance;
    }

    private final Map<Class, ExtensionDefinition> extensionDefinitionMap = new HashMap<>(128);

    private final Map<Class, Map<Class, List<Implementor>>> implementorsMapByExtension = new HashMap<>(128);

    private final Map<String, List<Implementor>> implementorsMapByPlugin = new HashMap<>(128);

    public void registerImplementor(Implementor<?> implementor) {
        Plugin plugin = implementor.getPlugin();

        List<Class> extensionClasses = getExtensionClasses(implementor.getTarget().getClass());

        for (Class extensionClass : extensionClasses) {
            ExtensionDefinition extensionDefinition = getExtensionDefinition(extensionClass);
            if (null != extensionDefinition) {
                Map<Class, List<Implementor>> implementorsMapByAnnotatedType = implementorsMapByExtension.computeIfAbsent(extensionClass,
                    p -> new HashMap<>(16)
                );

                List<Class> annotatedTypes = getAnnotatedTypes(implementor.getTarget().getClass());
                if ((!extensionDefinition.annotated()) || CollectionUtils.isEmpty(annotatedTypes)) {
                    List<Implementor> implementors = implementorsMapByAnnotatedType.computeIfAbsent(Void.class, p -> new LinkedList<>());
                    implementors.add(implementor);
                } else {
                    for (Class annotatedType : annotatedTypes) {
                        List<Implementor> implementors = implementorsMapByAnnotatedType.computeIfAbsent(annotatedType, p -> new LinkedList<>());
                        implementors.add(implementor);
                    }
                }

                if (null != plugin) {
                    List<Implementor> implementors = implementorsMapByPlugin.computeIfAbsent(plugin.getCode(), p -> new LinkedList<>());
                    implementors.add(implementor);
                }
            }
        }
    }

    private boolean hasAnnotatedType(Class clazz) {
        return clazz.getTypeParameters().length > 0;
    }

    private List<Class> getAnnotatedTypes(Class clazz) {
        List<Class> annotatedClassLists = new ArrayList<>();

        AnnotatedType annotatedType = clazz.getAnnotatedSuperclass();
        parseAnnotatedTypes(annotatedType, annotatedClassLists);
        for (AnnotatedType type : clazz.getAnnotatedInterfaces()) {
            parseAnnotatedTypes(type, annotatedClassLists);
        }

        return annotatedClassLists;
    }

    private void parseAnnotatedTypes(AnnotatedType annotatedType, List<Class> annotatedClassLists) {
        if (null != annotatedType && annotatedType.getType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) annotatedType.getType();
            Type[] types = parameterizedType.getActualTypeArguments();
            if (null != types && types.length >= 1 && types[0] instanceof Class) {
                Class annotatedClass = (Class) types[0];
                annotatedClassLists.add(annotatedClass);
            }
        }
    }

    public List<Implementor> getImplementors(Class extensionClass) {
        return getImplementors(extensionClass, Void.class);
    }

    public List<Implementor> getImplementors(Class extensionClass, Class annotatedType) {
        Map<Class, List<Implementor>> implementorsMapByAnnotatedType = implementorsMapByExtension.get(extensionClass);
        if (null != implementorsMapByAnnotatedType) {
            return implementorsMapByAnnotatedType.get(annotatedType);
        }
        return null;
    }

    public ExtensionDefinition getExtensionDefinition(Class extensionClass) {
        ExtensionDefinition extensionDefinition = extensionDefinitionMap.get(extensionClass);
        if (null == extensionDefinition) {
            synchronized (extensionDefinitionMap) {
                extensionDefinition = extensionDefinitionMap.get(extensionClass);
                if (null == extensionDefinition) {
                    Extension extension = (Extension) extensionClass.getAnnotation(Extension.class);
                    if (null != extension) {
                        boolean annotated = extension.annotated() && hasAnnotatedType(extensionClass);
                        extensionDefinition = new ExtensionDefinition(extensionClass, extension.name(), annotated);
                        extensionDefinitionMap.put(extensionClass, extensionDefinition);
                    }
                }
            }
        }

        return extensionDefinition;
    }

    private List<Class> getExtensionClasses(Class cls) {
        List<Class> set = new ArrayList<>();

        while (cls != null) {
            if (isExtensionClass(cls) && !set.contains(cls)) {
                set.add(cls);
            }

            Class[] interfaces = cls.getInterfaces();
            for (Class itf : interfaces) {
                List<Class> classes = getExtensionClasses(itf);
                for (Class c : classes) {
                    if (!set.contains(c)) {
                        set.add(c);
                    }
                }
            }

            cls = cls.getSuperclass();
        }

        return set;
    }

    private boolean isExtensionClass(Class cls) {
        if (cls.isInterface() && null != cls.getAnnotation(Extension.class)) {
            return true;
        }

        return false;
    }
}
