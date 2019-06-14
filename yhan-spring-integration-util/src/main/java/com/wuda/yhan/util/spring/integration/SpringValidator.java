package com.wuda.yhan.util.spring.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.Objects;

/**
 * 对<code>javax.validation.constraints</code>以及扩展(比如Hibernate)所定义各种规则
 * 进行校验.
 *
 * @author wuda
 */
@Component
public class SpringValidator {

    @Autowired
    private org.springframework.validation.Validator validator;

    /**
     * 校验给定的<i>target</i>.对校验结果调用{@link BeanPropertyBindingResult#hasErrors()}
     * ,检查是否有Error,如果返回<code>true</code>,则可以调用{@link BeanPropertyBindingResult#toString()}
     * 输出错误信息.
     *
     * @param target 被校验的对象
     * @return 校验结果
     */
    public BeanPropertyBindingResult validate(Object target) {
        Objects.requireNonNull(target, "null");
        BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
        validator.validate(target, beanPropertyBindingResult);
        return beanPropertyBindingResult;
    }
}
