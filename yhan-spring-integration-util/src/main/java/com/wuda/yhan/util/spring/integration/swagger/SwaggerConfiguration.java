package com.wuda.yhan.util.spring.integration.swagger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Swagger配置信息.在启动类上引入.
 *
 * @author wuda
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket docket() {
        String basePackage = swaggerProperties.getBasePackage();
        if (StringUtils.isBlank(basePackage)) {
            throw new RuntimeException("Swagger,包扫描路径未配置!该参数在" + SwaggerProperties.prefix + "前缀下");
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 包扫描路径,决定哪些接口出现在swagger-ui页面上
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // swagger-ui页面标题
                .title(swaggerProperties.getTitle() == null
                        ? "API交互文档" : swaggerProperties.getTitle())
                // swagger-ui页面描述
                .description(swaggerProperties.getDescription() == null
                        ? "Swagger API 交互文档" : swaggerProperties.getDescription())
                .build();
    }

}
