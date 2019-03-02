package com.wuda.yhan.util.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 描述POJO中属性的信息.
 *
 * @author wuda
 */
@Getter
@Setter
public class PojoFieldInfo {

    /**
     * 属性.
     */
    private Field field;
    /**
     * 属性的get方法.
     */
    private Method getter;
    /**
     * 属性的set方法.
     */
    private Method setter;

}
