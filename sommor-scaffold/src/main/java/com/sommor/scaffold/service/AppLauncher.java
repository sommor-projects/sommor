package com.sommor.scaffold.service;

import com.sommor.extensibility.config.Extension;
import org.springframework.context.ApplicationContext;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Extension(name = "应用启动器")
public interface AppLauncher {

    default void onLaunched(ApplicationContext context) {
    }
}
