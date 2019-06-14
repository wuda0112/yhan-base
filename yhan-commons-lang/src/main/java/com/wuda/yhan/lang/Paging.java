package com.wuda.yhan.lang;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页.
 *
 * @author wuda
 */
@ToString
@Getter
@Setter
public class Paging {

    /**
     * 页码.第一页是一,第二页是二,依次类推.
     */
    private int pageNumber = 1;
    /**
     * 数据量.
     */
    private int pageSize = 10;
}
