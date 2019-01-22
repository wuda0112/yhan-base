package com.wuda.yhan.lang.response;

/**
 * 状态码和描述信息.对{@link Result}的一个简明描述.
 *
 * @author wuda
 */
public interface ResultDesc {

    /**
     * 获取状态码.
     *
     * @return 状态码
     */
    int getCode();

    /**
     * 获取描述信息.
     *
     * @return message
     */
    String getMessage();

}
