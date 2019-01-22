package com.wuda.yhan.util.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link IsSetField}工具类.
 *
 * @author wuda
 */
public class IsSetFieldUtil {

    /**
     * 获取实体中调用过setter方法的属性.
     * <strong>注意</strong>必须是使用了{@link IsSetField}特性的类才适用此方法,
     * 否则即使field调用了setter方法,也将永远返回空.
     *
     * @param domain
     *         使用了{@link IsSetField}特性的实体类.
     * @return 所有调用过setter方法的属性, null - 如果没有任何field调用过setter方法,或者没有使用{@link IsSetField}特性
     */
    public static Field[] setterCalledFields(Object domain) {
        Class clazz = domain.getClass();
        Field[] allFields = clazz.getFields();
        if (allFields == null || allFields.length == 0) {
            return null;
        }
        List<Field> setterCalledFields = new ArrayList<>();
        for (Field field : allFields) {
            IsSetField isSetField = field.getAnnotation(IsSetField.class);
            if (isSetField != null) {
                field.setAccessible(true);
                boolean isSet;
                try {
                    isSet = field.getBoolean(domain);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (isSet) {
                    String refFieldName = isSetField.referenceField();
                    try {
                        setterCalledFields.add(clazz.getDeclaredField(refFieldName));
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (setterCalledFields.isEmpty()) {
            return null;
        }
        return setterCalledFields.toArray(new Field[setterCalledFields.size()]);
    }
}
