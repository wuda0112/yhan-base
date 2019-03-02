package com.wuda.yhan.util.commons;

import lombok.ToString;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

public class BeanUtilsTest {

    @Test
    public void getFieldList() {
        List<PojoFieldInfo> list = BeanUtils.getFieldInfoList(SimplePojo.class, false, false);
        if (list != null && !list.isEmpty()) {
            for (PojoFieldInfo pojoFieldInfo : list) {
                Method getter = pojoFieldInfo.getGetter();
                String getMethod = getter == null ? "没有" : getter.getName();
                Method setter = pojoFieldInfo.getSetter();
                String setMethod = setter == null ? "没有" : setter.getName();
                System.out.println("field: " + pojoFieldInfo.getField().getName()
                        + ", getter: " + getMethod
                        + ", setter: " + setMethod);
            }
        }
    }

    @Test
    public void fill() {
        SimplePojo pojo = new SimplePojo();
        BeanUtils.fill(pojo);
        System.out.println(pojo);
    }

    @ToString
    public static class SimplePojo {

        private String a;
        private int b;
        private Long c;

        private String getA() {
            return a;
        }

        private void setA(String a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setC(Long c) {
            this.c = c;
        }
    }
}
