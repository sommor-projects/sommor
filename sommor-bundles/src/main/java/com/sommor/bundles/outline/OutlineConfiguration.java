package com.sommor.bundles.outline;

import com.sommor.bundles.outline.model.AccessKeyStatus;
import com.sommor.core.component.status.StatusManager;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/3
 */
@Configuration
public class OutlineConfiguration {

    static {
        StatusManager.register("accessKeyStatus", AccessKeyStatus.values());
    }

}
