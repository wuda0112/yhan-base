package com.wuda.yhan.util.commons;

import org.junit.Test;

public class CharacterUtilsTest {

    @Test
    public void test() {
        String string = "你好hello world";
        System.out.println(CharacterUtils.getContinuousAlphabetAndDigit(string, 8));
    }

    @Test
    public void test1() {
        String string = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ０１２３４５６７８９";
        char[] chars = string.toCharArray();
        for (char cn : chars) {
            System.out.println(cn + "->" + CharacterUtils.cnAlphabetToEn(cn));
        }
    }

    @Test
    public void test2() {
        String string = "０１２３４５６７８９ａ";
        char[] chars = string.toCharArray();
        for (char cn : chars) {
            System.out.println(cn + "->" + CharacterUtils.cnDigitToEn(cn));
        }
    }
}
