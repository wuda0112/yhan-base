package com.wuda.yhan.util.commons;

import org.junit.Test;

public class RandomUtilsExtTest {

    @Test
    public void test() {
        System.out.println(RandomUtilsExt.randomString(10, CharacterUtils.UNICODE_HAN_START, CharacterUtils.UNICODE_HAN_END));
        System.out.println(RandomUtilsExt.randomChar());
        System.out.println(RandomUtilsExt.nextInt(new int[]{1, 8, 5}));
    }
}
