package com.wuda.yhan.util.commons;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void firstCharToUpperCaseTest() {
        String origin = "productName";
        String translate = StringUtil.firstCharToUpperCase(origin);
        Assert.assertEquals("ProductName", translate);
    }

    @Test
    public void changeCaseAtTest() {
        String origin = "ProductName";
        String translate = StringUtil.changeCaseAt(origin, 0, StringUtil.CaseEnum.lower);
        Assert.assertEquals("productName", translate);
    }
}
