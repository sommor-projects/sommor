package com.sommor.scaffold.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyu
 * @since 2019/1/20
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .globalOperationParameters(globalParameters())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sommor"))
            .paths(PathSelectors.any())
            .build();
    }

    private List<Parameter> globalParameters() {
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        builder
            .name("_lang").description("语言").modelRef(new ModelRef("string")).parameterType("header").required(false)
            .name("X-Authentication").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false)
            .build();
        parameters.add(builder.build());

        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Sommor APIs")
            .description("Sommor")
            .termsOfServiceUrl("http://sommor.com/")
            .contact(new Contact("yanguanwei", "http://sommor.com/", "yanguanwei@qq.com"))
            .version("1.0")
            .build();
    }


}
