package com.commor;

import com.sommor.extensibility.spring.SommorConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@SpringBootApplication
@Import(SommorConfiguration.class)
@MapperScan("com.sommor.bundle.**.repository")
@ComponentScan({
    "com.sommor"
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
