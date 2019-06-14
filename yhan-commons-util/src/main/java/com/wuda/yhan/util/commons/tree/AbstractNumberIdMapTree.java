package com.wuda.yhan.util.commons.tree;

/**
 * 提取一些公共的内容.
 *
 * @author wuda
 */
abstract class AbstractNumberIdMapTree {

    /**
     * 是否记录元素深度.
     */
    protected boolean recDepth;
    /**
     * 没有深度.
     */
    public final int NO_DEPTH = -1;

    void throwException0(Number id) {
        throw new IllegalStateException("元素的ID不能是:" + id + ",该值被系统本身使用");
    }

    void throwException1(Number id, Number pid) {
        throw new IllegalStateException("id=" + id + ",pid=" + pid + ",不能建立父子关系,"
                + "因为子元素和ROOT元素的ID相等,ROOT元素不能有父元素!");
    }

    void throwException2(Number oldParentId, Number parentId, Number childId) {
        throw new IllegalStateException("子元素[ ID = " + childId + " ],已经拥有父元素( ID=" + oldParentId + " )," +
                "因此,不能将[ ID=" + parentId + " ]的元素设置成它的父元素." +
                "子元素只能有一个父元素");
    }

    void throwException3(Number parentId, Number childId) {
        throw new IllegalStateException("想建立[ parent:" + parentId + " -> child:" + childId + " ]的父子关系," +
                "但是在树中已经存在[ parent:" + childId + " -> child:" + parentId + " ]的关系." +
                "父子关系不能互换");
    }

}
