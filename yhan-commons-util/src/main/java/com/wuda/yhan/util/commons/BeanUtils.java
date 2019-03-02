package com.wuda.yhan.util.commons;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * bean utils.
 *
 * @author wuda
 */
public class BeanUtils {

    /**
     * 获取POJO中属性的信息.
     *
     * @param clazz
     *         POJO clazz
     * @param mustHasGetter
     *         返回的属性是否必须包含get方法
     * @param mustHasSetter
     *         返回的属性是否必须包含set方法
     * @return 属性信息, null-如果该POJO中不包含任何属性,或者不满足给定条件
     */
    public static List<PojoFieldInfo> getFieldInfoList(Class<?> clazz, boolean mustHasGetter, boolean mustHasSetter) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return null;
        }
        List<PojoFieldInfo> list = new ArrayList<>(fields.length);
        for (Field field : fields) {
            String fieldName = field.getName();
            Method getter = getter(clazz, fieldName);
            if (getter == null && mustHasGetter) {
                continue;
            }
            Method setter = setter(clazz, fieldName);
            if (setter == null && mustHasSetter) {
                continue;
            }
            PojoFieldInfo pojoFieldInfo = new PojoFieldInfo();
            pojoFieldInfo.setField(field);
            pojoFieldInfo.setGetter(getter);
            pojoFieldInfo.setSetter(setter);
            list.add(pojoFieldInfo);
        }
        return list;
    }

    /**
     * 返回属性的get方法.
     *
     * @param clazz
     *         clazz
     * @param fieldName
     *         属性名称
     * @return get方法, 如果没有该属性, 或者该属性没有get方法, 则返回null.
     */
    public static Method getter(Class<?> clazz, String fieldName) {
        String getterName = JavaNamingUtil.genGetterMethodName(fieldName);
        Method getter;
        try {
            getter = clazz.getMethod(getterName);
        } catch (NoSuchMethodException e) {
            getter = null;
        }
        return getter;
    }

    /**
     * 返回属性的set方法.
     *
     * @param clazz
     *         clazz
     * @param fieldName
     *         属性名称
     * @return set方法, 如果没有该属性, 或者该属性没有set方法, 则返回null.
     */
    public static Method setter(Class<?> clazz, String fieldName) {
        Method setter;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            String setterName = JavaNamingUtil.genSetterMethodName(fieldName);
            setter = clazz.getMethod(setterName, field.getType());
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            setter = null;
        }
        return setter;
    }

    /**
     * 给pojo的属性填充值.pojo必须是标准的java bean,即属性必须拥有setter方法,并且是public访问权限,
     * 通过调用属性的setter方法为属性赋值.
     * 每个属性的值都是随机生成的.比如通过{@link RandomUtilsExt#randomChar()}生成随机的char.
     *
     * @param pojo
     *         标准的java pojo
     */
    public static void fill(Object pojo) {
        List<PojoFieldInfo> fieldInfos = getFieldInfoList(pojo.getClass(), false, true);
        if (fieldInfos == null || fieldInfos.isEmpty()) {
            return;
        }
        for (PojoFieldInfo fieldInfo : fieldInfos) {
            Method setter = fieldInfo.getSetter();
            Field field = fieldInfo.getField();
            Object arg = RandomValueAdapter.random(field.getType(), 10);
            try {
                setter.invoke(pojo, arg);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("给POJO填充值异常!POJO field= " + field + ", arg= " + arg, e);
            }
        }
    }

}
