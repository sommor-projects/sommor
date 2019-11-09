package com.sommor.extensibility;

import com.sommor.extensibility.config.PluginConfig;
import org.springframework.core.io.UrlResource;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/23
 */
public class PluginManager {

    private static final String PLUGIN_CONFIG_BASENAME = "sommor-plugin.config";

    private static PluginManager instance;

    private List<Plugin> plugins;

    public static PluginManager getInstance() {
        if (null == instance) {
            synchronized (PluginManager.class) {
                if (null == instance) {
                    PluginManager manager = new PluginManager();
                    manager.plugins = Collections.unmodifiableList(loadPluginsFromClasspath());

                    instance = manager;
                }
            }
        }

        return instance;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public Plugin match(Class extClass) {
        List<Plugin> plugins = this.plugins;
        String extClassName = extClass.getName();

        if (!CollectionUtils.isEmpty(plugins)) {
            for (Plugin plugin : plugins) {
                if (extClassName.startsWith(plugin.getPackageName())) {
                    return plugin;
                }
            }
        }

        return null;
    }

    private static List<Plugin> loadPluginsFromClasspath() {
        List<String> pluginClassNames = new ArrayList<>();
        String pluginConfigFileName = PLUGIN_CONFIG_BASENAME;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> urls = null;
        try {
            urls = classLoader.getResources(pluginConfigFileName);
        } catch (IOException e) {
            throw new RuntimeException("load resource["+pluginConfigFileName+"] exception", e);
        }

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            try {
                UrlResource urlResource = new UrlResource(url);
                BufferedReader bf = new BufferedReader(new InputStreamReader(urlResource.getInputStream()));
                String pluginClassName = bf.readLine();
                pluginClassNames.add(pluginClassName);
            } catch (Throwable e) {
                throw new RuntimeException("load plugin from classpath["+url+"] exception", e);
            }

        }

        List<Plugin> plugins = new ArrayList<>();

        for (String pluginClassName : pluginClassNames) {
            Class clazz;
            try {
                clazz = classLoader.loadClass(pluginClassName);
            } catch (Throwable e) {
                throw new RuntimeException("load plugin class["+pluginClassName+"] exception", e);
            }

            PluginConfig pluginConfig = (PluginConfig) clazz.getAnnotation(PluginConfig.class);
            if (null == pluginConfig) {
                throw new RuntimeException("Plugin Class["+pluginClassName+"] should be annotated PluginConfig");
            }

            Plugin plugin = new Plugin();
            plugin.setCode(pluginConfig.code().isEmpty() ? pluginClassName : pluginConfig.code());
            plugin.setClassName(pluginClassName);
            plugin.setPackageName(pluginClassName.substring(0, pluginClassName.lastIndexOf(".")));
            plugin.setName(pluginConfig.name());
            plugin.setPriority(pluginConfig.priority());

            plugins.add(plugin);
        }

        return plugins;
    }
}
