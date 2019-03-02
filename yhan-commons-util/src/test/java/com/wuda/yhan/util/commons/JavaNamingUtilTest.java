package com.wuda.yhan.util.commons;

import org.junit.Assert;
import org.junit.Test;

public class JavaNamingUtilTest {

    @Test
    public void toHumpNamingTest() {
        String origin = "product_name";
        String translate = JavaNamingUtil.toHumpNaming(origin, '_');
        Assert.assertTrue(translate.equals("productName"));
    }

    @Test
    public void addPrefixTest() {
        String origin = "ProductName";
        String translate = StringUtil.addPrefix(origin, JavaNamingUtil.prefix_get);
        Assert.assertEquals("getProductName", translate);
    }
}
