package com.sommor.bundles;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/9
 */
@SpringBootApplication
@MapperScan("com.sommor.bundles.**.repository")
public class SommorBundleConfiguration {
}
