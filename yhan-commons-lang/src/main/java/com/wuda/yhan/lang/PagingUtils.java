package com.wuda.yhan.lang;

/**
 * 分页工具类.
 *
 * @author wuda
 */
public class PagingUtils {

    /**
     * Mysql分页参数的<i>offset</i>从0开始,而我们定义的<i>page number</i>
     * 从1开始,因此需要简单转换一下.
     *
     * @param pageNumber page number
     * @return mysql paging offset
     */
    public static int getMysqlOffset(int pageNumber) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("page number 必须大于0");
        }
        return pageNumber - 1;
    }
}
