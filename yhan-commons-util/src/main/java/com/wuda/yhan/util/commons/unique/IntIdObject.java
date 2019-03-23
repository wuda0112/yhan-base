package com.wuda.yhan.util.commons.unique;

/**
 * id的数据类型是int.
 *
 * @author wuda
 */
public class IntIdObject implements NumberIdObject {

    /**
     * 唯一id.
     */
    private int id;

    public IntIdObject(int id) {
        this.id = id;
    }

    /**
     * get id.
     *
     * @return unique id
     */
    public int getId() {
        return this.id;
    }

    @Override
    public int compareTo(Object object) {
        if (object instanceof IntIdObject) {
            IntIdObject other = (IntIdObject) object;
            return Integer.compare(this.id, other.id);
        }
        return -1;
    }
}
