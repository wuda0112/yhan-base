package com.wuda.yhan.util.commons.unique;

/**
 * id的数据类型是long.
 *
 * @author wuda
 */
public class LongIdObject implements NumberIdObject {

    /**
     * 唯一标记.
     */
    private long id;

    LongIdObject(long id) {
        this.id = id;
    }

    /**
     * get id.
     *
     * @return unique id
     */
    public long getId() {
        return this.id;
    }

    @Override
    public int compareTo(Object object) {
        if (object instanceof LongIdObject) {
            LongIdObject other = (LongIdObject) object;
            return Long.compare(this.id, other.id);
        }
        return -1;
    }
}
