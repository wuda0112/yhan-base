package com.wuda.yhan.lang.response;

import com.wuda.yhan.lang.Paging;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 分页结果.
 *
 * @param <T> 结果的数据类型
 * @author wuda
 * @see Result
 */
@ToString
public class PagingResult<T> extends Paging {

    /**
     * 总数.
     */
    @Getter
    private int total;

    /**
     * 结果状态码.
     */
    @Getter
    private int code;
    /**
     * 状态码对应的描述信息.
     */
    @Getter
    private String message;
    /**
     * 返回实际结果.
     */
    @Getter
    private List<T> contents;

    /**
     * 构建结果对象.
     *
     * @param resultDesc 描述信息
     * @param contents   实际内容
     * @param total      总数
     * @param pageNumber 页码
     */
    public PagingResult(List<T> contents, ResultDesc resultDesc, int total, int pageNumber) {
        this.contents = contents;
        this.code = resultDesc.getCode();
        this.message = resultDesc.getMessage();
        this.total = total;
        setPageNumber(pageNumber);
        setPageSize(contents == null ? 0 : contents.size());
    }
}
