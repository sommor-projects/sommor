package com.commor.admin;

import com.sommor.bundles.SommorBundleConfiguration;
import com.sommor.core.SommorCoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@SpringBootApplication
@Import({
        SommorCoreConfiguration.class,
        SommorBundleConfiguration.class
})
public class SommorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SommorAdminApplication.class, args);
    }
}
