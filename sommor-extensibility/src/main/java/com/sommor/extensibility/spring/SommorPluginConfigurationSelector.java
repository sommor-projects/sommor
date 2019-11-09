package com.sommor.extensibility.spring;

import com.sommor.extensibility.Plugin;
import com.sommor.extensibility.PluginManager;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class SommorPluginConfigurationSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> selected = new ArrayList<>();

        List<Plugin> plugins = PluginManager.getInstance().getPlugins();
        if (! CollectionUtils.isEmpty(plugins)) {
            for (Plugin plugin : plugins) {
                selected.add(plugin.getClassName());
            }
        }

        return selected.toArray(new String[0]);
    }

}
