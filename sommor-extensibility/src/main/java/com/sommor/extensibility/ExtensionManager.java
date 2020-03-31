package com.sommor.extensibility;

import com.sommor.extensibility.config.Extension;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

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

    private final Map<Class, Map<String, List<Implementor>>> implementorsMapByExtension = new HashMap<>(128);

    private final Map<String, List<Implementor>> implementorsMapByPlugin = new HashMap<>(128);

    public void registerImplementor(Implementor<?> implementor) {
        Plugin plugin = implementor.getPlugin();

        List<Class> extensionClasses = getExtensionClasses(implementor.getTarget().getClass());

        for (Class extensionClass : extensionClasses) {
            ExtensionDefinition extensionDefinition = getExtensionDefinition(extensionClass);
            if (null != extensionDefinition) {
                Map<String, List<Implementor>> implementorsMapByAnnotatedType = implementorsMapByExtension.computeIfAbsent(extensionClass,
                    p -> new HashMap<>(16)
                );

                List<Class> annotatedTypes = getAnnotatedTypes(implementor.getTarget().getClass(), extensionClass);
                if ((!extensionDefinition.annotated()) || CollectionUtils.isEmpty(annotatedTypes)) {
                    List<Implementor> implementors = implementorsMapByAnnotatedType.computeIfAbsent(Void.class.getName(), p -> new LinkedList<>());
                    implementors.add(implementor);
                } else {
                    String key = annotatedTypes.stream().map(p->p.getName()).collect(Collectors.joining("~"));
                    List<Implementor> implementors = implementorsMapByAnnotatedType.computeIfAbsent(key, p -> new LinkedList<>());
                    implementors.add(implementor);
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

    private List<Class> getAnnotatedTypes(Class clazz, Class extensionClass) {
        List<Class> annotatedClassLists = parseExtensionAnnotatedTypes(clazz, extensionClass, Collections.emptyMap());
        if (! annotatedClassLists.isEmpty()) {
            return annotatedClassLists;
        }

        if (annotatedClassLists.isEmpty()) {
            Map<String, Class> annotatedTypeMap = new HashMap<>();
            AnnotatedType superAnnotatedType = clazz.getAnnotatedSuperclass();;
            while (null != superAnnotatedType) {
                Class superClass = parseClass(superAnnotatedType);
                if (null != superClass) {
                    annotatedTypeMap.putAll(parseAnnotatedTypeMap(superAnnotatedType));
                    if (! annotatedTypeMap.isEmpty()) {
                        annotatedClassLists = parseExtensionAnnotatedTypes(superClass, extensionClass, annotatedTypeMap);
                        if (! annotatedClassLists.isEmpty()) {
                            break;
                        }
                    }
                    superAnnotatedType = superClass.getAnnotatedSuperclass();
                } else {
                    break;
                }
            }
        }

        return annotatedClassLists;
    }

    private Class parseClass(AnnotatedType annotatedType) {
        Type type = annotatedType.getType();
        if (type instanceof ParameterizedType) {
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType instanceof Class) {
                return (Class) rawType;
            }
        }
        return null;
    }

    private Map<String, Class> parseAnnotatedTypeMap(AnnotatedType annotatedType) {
        if (null != annotatedType && annotatedType.getType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) annotatedType.getType();
            Type[] types = parameterizedType.getActualTypeArguments();
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class) {
                Class clazz = (Class) rawType;
                TypeVariable<Class>[] tps  = clazz.getTypeParameters();
                if (tps != types && tps.length >= 1) {
                    Map<String, Class> map = new HashMap<>();
                    for (int i=0; i<types.length; i++) {
                        Type type = types[i];
                        if (type instanceof Class) {
                            String typeName = tps[i].getName();
                            map.put(typeName, (Class) type);
                        }
                    }
                    return map;
                }
            }

        }
        return Collections.emptyMap();
    }

    private List<Class> parseExtensionAnnotatedTypes(Class clazz, Class extensionClass, Map<String, Class> annotatedTypeMap) {
        for (AnnotatedType type : clazz.getAnnotatedInterfaces()) {
            List<Class> annotatedClassLists = parseExtensionAnnotatedTypes(type, extensionClass, annotatedTypeMap);
            if (! annotatedClassLists.isEmpty()) {
                return annotatedClassLists;
            }
        }
        return Collections.emptyList();
    }

    private List<Class> parseExtensionAnnotatedTypes(AnnotatedType annotatedType, Class extensionClass, Map<String, Class> annotatedTypeMap) {
        if (null != annotatedType && annotatedType.getType() instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) annotatedType.getType();
            Type rawType = parameterizedType.getRawType();
            if (rawType == extensionClass) {
                Type[] types = parameterizedType.getActualTypeArguments();
                if (null != types && types.length >= 1) {
                    List<Class> annotatedClassLists = new ArrayList<>();
                    for (Type type : types) {
                        if (type instanceof Class) {
                            Class annotatedClass = (Class) type;
                            annotatedClassLists.add(annotatedClass);
                        } else if (type instanceof TypeVariable) {
                            TypeVariable typeVariable = (TypeVariable) type;
                            if (annotatedTypeMap.containsKey(typeVariable.getName())) {
                                annotatedClassLists.add(annotatedTypeMap.get(typeVariable.getName()));
                            }
                        }
                    }
                    return annotatedClassLists;
                }
            }
        }

        return Collections.emptyList();
    }

    public List<Implementor> getImplementors(Class extensionClass) {
        return getImplementors(extensionClass, Void.class);
    }

    public <Ext> List<Ext> getImplements(Class<Ext> extensionClass) {
        return getImplements(extensionClass, Void.class);
    }

    public <Ext> List<Ext> getImplements(Class<Ext> extensionClass, Class... annotatedTypes) {
        return getImplementors(extensionClass, annotatedTypes)
                .stream()
                .map(p -> (Ext) p.getTarget())
                .collect(Collectors.toList());
    }

    public List<Implementor> getImplementors(Class extensionClass, Class... annotatedTypes) {
        Map<String, List<Implementor>> implementorsMapByAnnotatedType = implementorsMapByExtension.get(extensionClass);
        List<Implementor> implementors = null;

        if (null != implementorsMapByAnnotatedType) {
            String key = Arrays.stream(annotatedTypes).map(p -> p.getName()).collect(Collectors.joining("~"));
            implementors = implementorsMapByAnnotatedType.get(key);
        }

        return null == implementors ? Collections.emptyList() : implementors;
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
