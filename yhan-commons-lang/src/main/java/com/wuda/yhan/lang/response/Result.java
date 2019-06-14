package com.wuda.yhan.lang.response;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 给客户端的响应结果.
 *
 * @param <T> 响应中实际结果的类型
 * @author wuda
 * @see PagingResult
 */
@ToString
public class Result<T> implements Serializable {

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
    private T content;

    /**
     * 构建结果对象.
     *
     * @param content    实际内容
     * @param resultDesc 描述信息
     */
    public Result(T content, ResultDesc resultDesc) {
        this.code = resultDesc.getCode();
        this.message = resultDesc.getMessage();
        this.content = content;
    }
}
