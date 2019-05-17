package com.wuda.yhan.util.commons.tree;

/**
 * 提取一些公共的内容.
 *
 * @author wuda
 */
abstract class AbstractNumberIdMapTree {

    /**
     * 是否记录节点深度.
     */
    protected boolean recDepth;
    /**
     * 没有深度.
     */
    public final int NO_DEPTH = -1;

    void throwException0(Number id) {
        throw new IllegalStateException("节点的ID不能是:" + id + ",改值被系统本身使用");
    }

    void throwException1(Number id, Number pid) {
        throw new IllegalStateException("id=" + id + ",pid=" + pid + ",不能建立父子关系,"
                + "因为子节点和ROOT节点的ID相等,ROOT节点不能有父节点!");
    }

    void throwException2(Number oldParentId, Number parentId, Number childId) {
        throw new IllegalStateException("子节点[ ID = " + childId + " ],已经拥有父节点( ID=" + oldParentId + " )," +
                "因此,不能将[ ID=" + parentId + " ]的节点设置成它的父节点." +
                "子节点只能有一个父节点");
    }

    void throwException3(Number parentId, Number childId) {
        throw new IllegalStateException("想建立[ parent:" + parentId + " -> child:" + childId + " ]的父子关系," +
                "但是在树中已经存在[ parent:" + childId + " -> child:" + parentId + " ]的关系." +
                "父子关系不能互换");
    }

}
