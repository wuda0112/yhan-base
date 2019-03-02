package com.wuda.yhan.util.commons;

import org.apache.commons.lang3.RandomUtils;

/**
 * 根据数据类型返回对应的随机值.
 *
 * @author wuda
 */
public class RandomValueAdapter {

    /**
     * 根据给定的类型,生成对应类型的随机值.
     * 目前能够处理的数据类型有:
     * <pre>
     *     {@link Byte}
     *     {@link Short}
     *     {@link Integer}
     *     {@link Long}
     *     {@link Float}
     *     {@link Double}
     *     {@link Boolean}
     *     {@link Character}
     *     {@link String}
     * </pre>
     *
     * @param dataType
     *         数据类型
     * @param length
     *         如果是{@link String},则生成给定长度的字符串,如果是{@link Byte},则生成给定长度的字节数组
     * @return 随机值
     */
    public static Object random(Class<?> dataType, int length) {
        JavaBasicDataTypeEnum dataTypeEnum = JavaBasicDataTypeEnum.of(dataType);
        switch (dataTypeEnum) {
            case BYTE:
                return RandomUtils.nextBytes(length);
            case SHORT:
                return RandomUtils.nextInt(-32768, 32767 + 1);
            case INT:
                return RandomUtils.nextInt();
            case LONG:
                return RandomUtils.nextLong();
            case FLOAT:
                return RandomUtils.nextFloat();
            case DOUBLE:
                return RandomUtils.nextDouble();
            case BOOLEAN:
                return RandomUtils.nextBoolean();
            case CHAR:
                return RandomUtilsExt.randomChar();
            case STRING:
                return RandomUtilsExt.randomString(length, CharacterUtils.UNICODE_HAN_START, CharacterUtils.UNICODE_HAN_END);
            case UNKNOWN:
                return null;
        }
        return null;
    }
}