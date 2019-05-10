package com.wuda.yhan.util.commons;

import lombok.ToString;

import org.junit.Test;

import com.wuda.yhan.util.commons.BeanUtils.AnnotationContainsPolicy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeanUtilsTest {
	
	private <T extends Annotation> Set<Class<T>> get(){
		Set<Class<T>> annotationClassSet=new HashSet<>();
    	annotationClassSet.add((Class<T>)IsSetField.class);
    	annotationClassSet.add((Class<T>)Deprecated.class);
		return annotationClassSet;
	}

    @Test
    public void getFieldList() {
    	
        List<PojoFieldInfo> list = BeanUtils.getFieldInfoList(SimplePojo.class, false, false,get(), AnnotationContainsPolicy.CONTAINS_ZERO);
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
        BeanUtils.populateRandomValue(pojo);
        System.out.println(pojo);
    }

    @ToString
    public static class SimplePojo {

    	@IsSetField(referenceField = "")
        private String a;
    	@Deprecated
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
