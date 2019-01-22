package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.wuda.yhan.util.commons.unique.IntIdObject;

/**
 * 树形结构,{@link RelationshipTree.Node}的元素的Id是int类型.
 *
 * @param <T>
 *         节点中元素的类型
 */
public class IntIdRelationshipTree<T extends IntIdObject> extends RelationshipTree<T> {
    /**
     * key - ID ,value - node. id索引.
     */
    private HashIntObjMap<RelationshipTree.Node<T>> ID2NodeMap = HashIntObjMaps.newMutableMap();

    /**
     * 生成空树,并且指定root节点的id.
     *
     * @param rootId
     *         root节点的id
     */
    public IntIdRelationshipTree(int rootId) {
        ID2NodeMap.put(rootId, root);
    }

    @Override
    protected void putToContainer(Node<T> node) {
        ID2NodeMap.put(node.getElement().getId(), node);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated {@link #getById(int)}替代
     */
    @Override
    public Node<T> getById(Number id) {
        throw new UnsupportedOperationException();
    }

    public Node<T> getById(int id) {
        return ID2NodeMap.get(id);
    }


    @Deprecated
    @Override
    public Node<T> removeById(Number id) {
        throw new UnsupportedOperationException();
    }

    public Node<T> updateById(T element) {
        if (element != null) {
            Node<T> node = getById(element.getId());
            if (node != null) {
                node.element = element;
                return node;
            }
        }
        return null;
    }

    /**
     * 检查关系是否允许.父子关系互换,这种情况就是不允许的.
     * 比如树中已经存一个ID/PID关系是:1/2,而当前检查的ID/PID的关系是:2/1,这是不允许的.
     *
     * @param id
     *         id
     * @param pid
     *         pid
     */
    public void checkRelationship(int id, int pid) {
        Node<T> candidateChild = getById(id);
        Node<T> candidateParent = getById(pid);
        if (relationshipSwitch(candidateChild, candidateParent)) {
            throw new RuntimeException("在已经存在的树中已经有父子关系,不能互换父子关系!它们的id是:" + id + "和" + pid);
        }
    }

    /**
     * 给定的两个节点都必须存在,如果任意一个不存在,都不会有任何影响.
     *
     * @param id
     *         child id
     * @param pid
     *         parent id
     */
    public void createRelationship(int id, int pid) {
        RelationshipTree.Node<T> parent = this.getById(pid);
        if (parent == null) {
            return;
        }
        RelationshipTree.Node<T> child = this.getById(id);
        if (child == null) {
            return;
        }
        createRelationship(parent, child);
    }
}
