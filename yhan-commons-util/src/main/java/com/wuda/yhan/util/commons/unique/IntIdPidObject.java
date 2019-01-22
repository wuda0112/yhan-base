package com.wuda.yhan.util.commons.unique;

/**
 * id和pid的数据类型是int.
 *
 * @author wuda
 */
public abstract class IntIdPidObject extends IntIdObject implements NumberIdPidObject {

    private int pid;

    protected IntIdPidObject(int id, int pid) {
        super(id);
        this.pid = pid;
    }

    public int getPid() {
        return pid;
    }
}
