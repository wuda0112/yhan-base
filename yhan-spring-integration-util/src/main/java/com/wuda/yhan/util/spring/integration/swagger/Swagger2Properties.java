package com.wuda.yhan.util.spring.integration.swagger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.wuda.yhan.util.spring.integration.swagger.Swagger2Properties.prefix;

/**
 * swagger2 属性信息.
 *
 * @author wuda
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = prefix)
public class Swagger2Properties {

    final static String prefix = "swagger2";

    private String basePackage;
    private String title;
    private String description;
}
