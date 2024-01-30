/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.config;

import cn.hutool.core.collection.CollectionUtil;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
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

import java.util.List;

/**
 * swagger配置
 *
 * @author oderway
 * @date 2020/3/11 15:05
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        Parameter parameter = new ParameterBuilder()
                .name("Authorization")
                .description("token令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> parameters = CollectionUtil.newArrayList();
        parameters.add(parameter);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .useDefaultResponseMessages(false); // 取消默认的响应编码 200编码除外
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("OrderWay Doc")
                .description("OrderWay Doc文档")
                .termsOfServiceUrl("https://www.OrderWay.cn")
                .contact(new Contact("orderway", "https://www.orderway.cn", ""))
                .version("1.0")
                .build();
    }

}
