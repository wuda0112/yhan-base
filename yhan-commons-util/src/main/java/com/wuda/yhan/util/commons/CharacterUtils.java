package com.wuda.yhan.util.commons;

/**
 * Character util.
 *
 * @author wuda
 */
public class CharacterUtils {

    /**
     * Unicode,汉语范围的起始值.
     */
    public static int UNICODE_HAN_START = 0x4E00;
    /**
     * Unicode,汉语范围的结束值.
     */
    public static int UNICODE_HAN_END = 0x9FEF;

    /**
     * 是否字母,包括
     * <ul>
     * <li>ASCII的大小写字母</li>
     * <li>中文输入法下的大小写字母</li>
     * </ul>
     *
     * @param ch char
     * @return true-如果是
     */
    public static boolean isAlphabet(char ch) {
        if (ch >= 65 && ch <= 90) { // ASCII中的大写字母
            return true;
        } else if (ch >= 97 && ch <= 122) { // ASCII中的小写字母
            return true;
        }
        return isCnAlphabet(ch);
    }

    /**
     * 是否中文输入法模式下的字母.
     *
     * @param ch char
     * @return true-如果是
     */
    public static boolean isCnAlphabet(char ch) {
        if (ch >= 65313 && ch <= 65338) { // 中文输入法下的大写
            return true;
        } else if (ch >= 65345 && ch <= 65370) {// 中文输入法下的小写
            return true;
        }
        return false;
    }

    /**
     * 是否中文输入法模式下的数字.
     *
     * @param ch char
     * @return true-如果是
     */
    public static boolean isCnDigit(char ch) {
        if (ch >= 65296 && ch <= 65305) {
            return true;
        }
        return false;
    }

    /**
     * 从给定的<i>offset</i>处(包含)开始,向后获取连续的字母或者数字.
     *
     * @param string string
     * @param offset 开始位置,包含
     * @return count, 连续的字母和数字的个数,如果返回<i>0</i>,则表示给定的<i>offset</i>处也不是字母或者数字,因此完全没必要往后继续.
     */
    public static int getContinuousAlphabetAndDigit(String string, int offset) {
        int count = 0;
        char ch;
        while (offset < string.length()) {
            ch = string.charAt(offset);
            if (isAlphabet(ch) || Character.isDigit(ch)) {
                offset++;
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * 对于给定的<i>ch</i>调用{@link #isCnAlphabet(char)},如果返回<code>true</code>,
     * 则转换成对应的英文字母.否则不做任何转换,直接返回原值.
     *
     * @param ch char
     * @return char
     */
    public static char cnAlphabetToEn(char ch) {
        int cnMin = 65313;
        int enMin = 65;
        char map = ch;
        if (isCnAlphabet(ch)) {
            map = (char) (enMin + (ch - cnMin));
        }
        return map;
    }

    /**
     * 对于给定的<i>ch</i>调用{@link #isCnDigit(char)} ,如果返回<code>true</code>,
     * 则转换成对应的英文数字.否则不做任何转换,直接返回原值.
     *
     * @param ch char
     * @return char
     */
    public static char cnDigitToEn(char ch) {
        int cnMin = 65296;
        int enMin = 48;
        char map = ch;
        if (isCnDigit(ch)) {
            map = (char) (enMin + (ch - cnMin));
        }
        return map;
    }
}
