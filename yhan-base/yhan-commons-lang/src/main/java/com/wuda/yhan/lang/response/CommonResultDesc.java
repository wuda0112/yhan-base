package com.wuda.yhan.lang.response;

/**
 * 通用的,系统内置的状态码和描述信息.
 *
 * @author wuda
 */
public enum CommonResultDesc implements ResultDesc {

    /**
     * fail.
     */
    FAIL(0, "fail"),
    /**
     * ok.
     */
    OK(1, "ok");

    /**
     * 状态码.
     */
    private int code;
    /**
     * 描述.
     */
    private String message;

    CommonResultDesc(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
