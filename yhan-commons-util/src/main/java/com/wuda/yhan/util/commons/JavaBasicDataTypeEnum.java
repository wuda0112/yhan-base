package com.wuda.yhan.util.commons;

/**
 * java基本的数据类型.
 * 除了<a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">Primitive Data Types</a>
 * 这里定义的以外,这里再加上{@link String}.
 *
 * @author wuda
 */
public enum JavaBasicDataTypeEnum {

    BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR, STRING, UNKNOWN;

    /**
     * 将给定的dataType映射到{@link JavaBasicDataTypeEnum}枚举.如果不是基本的数据类型,则返回{@link #UNKNOWN}.
     *
     * @param dataType
     *         只能是基本的数据类型.
     * @return 对应的枚举
     */
    public static JavaBasicDataTypeEnum of(Class dataType) {
        JavaBasicDataTypeEnum javaBasicDataTypeEnum = UNKNOWN;
        if (dataType.equals(Byte.class)) {
            javaBasicDataTypeEnum = BYTE;
        } else if (dataType.equals(Short.class)) {
            javaBasicDataTypeEnum = SHORT;
        } else if (dataType.equals(Integer.class)) {
            javaBasicDataTypeEnum = INT;
        } else if (dataType.equals(Long.class)) {
            javaBasicDataTypeEnum = LONG;
        } else if (dataType.equals(Float.class)) {
            javaBasicDataTypeEnum = FLOAT;
        } else if (dataType.equals(Double.class)) {
            javaBasicDataTypeEnum = DOUBLE;
        } else if (dataType.equals(Boolean.class)) {
            javaBasicDataTypeEnum = BOOLEAN;
        } else if (dataType.equals(Character.class)) {
            javaBasicDataTypeEnum = CHAR;
        } else if (dataType.equals(String.class)) {
            javaBasicDataTypeEnum = STRING;
        }
        return javaBasicDataTypeEnum;
    }
}
