package com.wuda.yhan.util.spring.integration;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常用的配置信息.
 */
@Component
@Data
@ToString
public class ApplicationEnvironment {

    @Value("#{@environment['spring.application.name']}")
    private String springApplicationName;
}
