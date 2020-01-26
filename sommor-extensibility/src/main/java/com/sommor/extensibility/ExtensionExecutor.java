package com.sommor.extensibility;

import com.sommor.extensibility.callback.P1RCallback;
import com.sommor.extensibility.callback.P1VCallback;
import com.sommor.extensibility.reducer.Reducer;
import com.sommor.extensibility.reducer.Reducers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class ExtensionExecutor<Ext> {

    private Class<Ext> extClass;

    public ExtensionExecutor(Class<Ext> extClass) {
        this.extClass = extClass;
    }

    public <R> R executeFirst(P1RCallback<Ext, R> callback) {
        return this.execute(callback, Reducers.first());
    }

    public <R> R executeFirstNotNull(Object annotatedInstance, P1RCallback<Ext, R> callback) {
        return this.execute(annotatedInstance, callback, Reducers.firstNotNull());
    }

    public <R> R executeFirstNotNull(P1RCallback<Ext, R> callback) {
        return this.execute(callback, Reducers.firstNotNull());
    }

    public <R> R executeFirst(Class annotatedType, P1RCallback<Ext, R> callback) {
        return this.execute(annotatedType, callback, Reducers.first());
    }

    public <R> List<R> executeAll(P1RCallback<Ext, R> callback) {
        return this.execute(callback, Reducers.collect());
    }

    public <R, RR> RR execute(P1RCallback<Ext, R> callback, Reducer<R, RR> reducer) {
        return execute(Void.class, callback, reducer);
    }

    @SuppressWarnings("unchecked")
    public <R, RR> RR execute(Object annotatedInstance, P1RCallback<Ext, R> callback, Reducer<R, RR> reducer) {
        Class<Ext> extClass = this.extClass;
        Class annotatedType = parseAnnotatedType(annotatedInstance);

        List<Implementor> implementors = ExtensionManager.getInstance().getImplementors(extClass, annotatedType);
        for (Implementor ei : implementors) {
            Ext ext = (Ext) ei.getTarget();

            R result = callback.apply(ext);
            if (reducer.reduce(result, ei)) {
                break;
            }
        }

        return reducer.getResult();
    }

    public void run(P1VCallback<Ext> callback) {
        run(Void.class, callback);
    }

    @SuppressWarnings("unchecked")
    public void run(Object annotatedInstance, P1VCallback<Ext> callback) {
        Class<Ext> extClass = this.extClass;
        Class annotatedType = parseAnnotatedType(annotatedInstance);

        List<Implementor> implementors = ExtensionManager.getInstance().getImplementors(extClass, annotatedType);
        if (null != implementors && ! implementors.isEmpty()) {
            for (Implementor ei : implementors) {
                callback.apply((Ext) ei.getTarget());
            }
        }
    }

    private final static Map<Class, ExtensionExecutor> EXTENSION_EXECUTOR_MAP = new HashMap<>(128);

    @SuppressWarnings("unchecked")
    public static <Ext> ExtensionExecutor<Ext> of(Class<Ext> extClass) {
        ExtensionExecutor<Ext> extensionExecutor = EXTENSION_EXECUTOR_MAP.get(extClass);
        if (null == extensionExecutor) {
            synchronized (EXTENSION_EXECUTOR_MAP) {
                extensionExecutor = EXTENSION_EXECUTOR_MAP.get(extClass);
                if (null == extensionExecutor) {
                    extensionExecutor = new ExtensionExecutor<>(extClass);
                    EXTENSION_EXECUTOR_MAP.put(extClass, extensionExecutor);
                }
            }
        }

        return extensionExecutor;
    }

    private static final Map<Class, Object> EXTENSION_PROXY_MAP = new HashMap<>(128);

    private static InvocationHandler handler = (proxy, method, args) -> {
        ExtensionManager em = ExtensionManager.getInstance();

        Class extensionClass = method.getDeclaringClass();
        ExtensionDefinition ed = em.getExtensionDefinition(extensionClass);

        Class annotatedType = Void.class;
        if (ed.annotated()) {
            annotatedType = parseAnnotatedType(args[0]);
        }

        List<Implementor> implementors = em.getImplementors(extensionClass, annotatedType);
        if (null == implementors || implementors.isEmpty()) {
            return null;
        }

        return method.invoke(implementors.get(0).getTarget(), args);
    };

    private static Class parseAnnotatedType(Object o) {
        if (o instanceof Class) {
            return (Class) o;
        }

        Class annotatedType;
        if (o instanceof Proxy) {
            annotatedType = o.getClass().getInterfaces()[0];
        } else {
            annotatedType = o.getClass();
        }

        return annotatedType;
    }

    @SuppressWarnings("unchecked")
    public static <Ext> Ext proxyOf(Class<Ext> extClass) {
        Ext ext = (Ext) EXTENSION_PROXY_MAP.get(extClass);
        if (null == ext) {
            synchronized (EXTENSION_PROXY_MAP) {
                ext = (Ext) EXTENSION_PROXY_MAP.get(extClass);
                if (null == ext) {
                    ext = (Ext) Proxy.newProxyInstance(extClass.getClassLoader(), new Class[] {extClass}, handler);
                    EXTENSION_PROXY_MAP.put(extClass, ext);
                }
            }
        }
        return ext;
    }
}
