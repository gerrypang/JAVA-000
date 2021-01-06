package com.gerry.pang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Value("${swagger.enabled}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.enable(enableSwagger)
    			.apiInfo(apiInfo())
    			.select()
    			.apis(RequestHandlerSelectors.basePackage("com.gerry.pang.order.controller"))
//				为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//				为有@ApiOperation注解的方法生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
    			.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("order-server restful APIs")
                .description("order server restful APIs")
                .termsOfServiceUrl("http://www.demo.com/")
                .version("1.0")
                .build();
    }
}
