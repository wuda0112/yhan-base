package com.wuda.yhan.util.commons.unique;

/**
 * id和pid的数据类型是long.
 *
 * @author wuda
 */
public class LongIdPidObject extends LongIdObject implements NumberIdPidObject {

    private long pid;

    protected LongIdPidObject(long id, long pid) {
        super(id);
        this.pid = pid;
    }

    public long getPid() {
        return pid;
    }
}
