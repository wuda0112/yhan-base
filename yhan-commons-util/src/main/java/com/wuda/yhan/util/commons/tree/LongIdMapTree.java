package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.LongCollection;
import com.koloboke.collect.map.hash.*;
import com.koloboke.collect.set.hash.HashLongSets;
import com.wuda.yhan.util.commons.unique.LongIdObject;

import java.util.function.Consumer;

/**
 * 用Map的方式实现树形关系.树中节点都有一个唯一ID，并且是<code>long</code>类型.
 *
 * @param <E> 树中节点的类型
 * @author wuda
 */
public class LongIdMapTree<E extends LongIdObject> extends AbstractNumberIdMapTree {

    /**
     * 根节点.
     */
    private E root;

    /**
     * 保存关系,子节点指向父节点.key - id ,value - parent id.
     */
    private HashLongLongMap id2PidMap;
    /**
     * 保存关系,父节点指向它的所有字节点.
     * key - parent id , value - 所有的子节点ID的集合.
     */
    private HashLongObjMap<LongCollection> pid2ChildrenMap;
    /**
     * 保存数据.key - id , value - 此id对应的节点.
     */
    private HashLongObjMap<E> id2NodeMap;
    /**
     * 保存数据.key - id , value - 此id对应的节点的深度.
     */
    private HashLongIntMap id2DepthMap;

    /**
     * 如果node ID等于改值,表示此ID不存在.
     */
    public final long NOT_EXIST = Long.MIN_VALUE;

    /**
     * 构造树.
     *
     * @param root     根节点
     * @param recDepth 是否记录节点的深度,如果记录的话,多使用一些内存
     */
    public LongIdMapTree(E root, boolean recDepth) {
        validateNode(root);
        this.root = root;
        this.recDepth = recDepth;
        init();
        addNode(root);
        setDepth(root);
    }

    /**
     * 构造树.不记录节点深度.
     *
     * @param root 根节点
     */
    public LongIdMapTree(E root) {
        this(root, false);
    }

    /**
     * init.
     */
    private void init() {
        id2PidMap = HashLongLongMaps.newMutableMap();
        pid2ChildrenMap = HashLongObjMaps.newMutableMap();
        id2NodeMap = HashLongObjMaps.newMutableMap();
        if (recDepth) {
            id2DepthMap = HashLongIntMaps.newMutableMap();
        }
    }

    /**
     * 为两个节点建立父子关系.
     *
     * @param parent 作为父节点
     * @param child  作为子节点
     */
    public void createRelationship(E parent, E child) {
        validateNode(parent);
        validateNode(child);
        validateRelationship(parent, child);
        boolean hasRelationship = alreadyHasRelationship(parent, child);
        if (hasRelationship) {
            return;
        }
        long childId = child.getId();
        long parentId = parent.getId();

        id2PidMap.put(childId, parentId);
        LongCollection children = pid2ChildrenMap.get(parentId);
        if (children == null) {
            children = HashLongSets.newMutableSet();
            pid2ChildrenMap.put(parentId, children);
        }
        children.add(childId);

        setDepth(child);

        addNode(parent);
        addNode(child);
    }

    /**
     * 设置节点的深度.节点的深度等于它父节点深度加一.如果当前节点的深度发生变化,
     * 那么它的所有子节点的深度也会相应的更新.
     *
     * @param node node
     */
    private void setDepth(E node) {
        if (recDepth) {
            int depth = 0;
            long id = node.getId();
            if (id != root.getId()) {
                long pid = getParent(id);
                int parentDepth = getDepth(pid);
                depth = parentDepth + 1;
            }
            if (depth != getDepth(id)) {
                // 深度发生变化才更新
                id2DepthMap.put(id, depth);
                // 更新当前节点下的所有子节点的深度
                long[] children = getChildren(id);
                if (children != null && children.length > 0) {
                    for (long child : children) setDepth(get(child));
                }
            }
        }
    }

    /**
     * 获取根节点.
     *
     * @return root
     */
    public E getRoot() {
        return root;
    }

    /**
     * 获取给定id所代表的节点的所有子节点id.
     *
     * @param id 节点id
     * @return 这个节点id下的所有子节点
     */
    public long[] getChildren(long id) {
        LongCollection children = pid2ChildrenMap.get(id);
        if (children == null) {
            return null;
        }
        return children.toLongArray();
    }

    /**
     * 获取给定节点的深度.
     *
     * @param id 节点id
     * @return 深度, 如果{@link #recDepth}设置为<code>true</code>才记录深度
     */
    public int getDepth(long id) {
        if (!recDepth) {
            return NO_DEPTH;
        }
        return id2DepthMap.get(id);
    }

    /**
     * 获取ID对应的节点.
     *
     * @param id id
     * @return node
     */
    public E get(long id) {
        return id2NodeMap.get(id);
    }

    /**
     * 获取节点的祖先.
     *
     * @param id    node id
     * @param count 查找祖先的个数
     * @return 数组中第0个元素是直接父节点, 第一个元素是父节点的父节点, 依次类推.
     * 数组中的元素, 不一定都是该节点的祖先的ID,
     * 比如有个节点总共只有一个上级节点,但是你并不知道这个情况,你想取回它的三个祖先,
     * 在这种情况下,第0个元素是父节点ID,第一和第二个元素的值就是{@link #NOT_EXIST}
     */
    public long[] getAncestor(long id, int count) {
        long[] ancestors = initArray(count, NOT_EXIST);
        if (ancestors == null) {
            return null;
        }
        int index = 0;
        while (index < count) {
            long pid = getParent(id);
            if (pid == NOT_EXIST) {
                break;
            }
            ancestors[index] = pid;
            id = pid; // 向上推进
            index++;
        }
        return ancestors;
    }

    /**
     * 初始化int数组.
     *
     * @param capacity  数组容量
     * @param initValue 初始值
     * @return 如果容量小于等于0, 则返回null
     */
    private long[] initArray(int capacity, long initValue) {
        if (capacity <= 0) {
            return null;
        }
        long[] array = new long[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = initValue;
        }
        return array;
    }

    /**
     * 获取直接父节点.
     *
     * @param id node id
     * @return 父节点, 如果不存在则返回{@link #NOT_EXIST}
     */
    public long getParent(Long id) {
        return id2PidMap.getOrDefault(id.longValue(), NOT_EXIST);
    }

    /**
     * 校验节点.
     *
     * @param node node
     */
    private void validateNode(E node) {
        if (node.getId() == NOT_EXIST) {
            throwException0(NOT_EXIST);
        }
    }

    /**
     * 验证两个节点的关系.
     *
     * @param parent parent
     * @param child  child
     */
    private void validateRelationship(E parent, E child) {
        long childId = child.getId();
        if (childId == root.getId()) {
            throwException1(childId, parent.getId());
        }
        long parentId = parent.getId();
        long oldParentId = id2PidMap.getOrDefault(childId, NOT_EXIST);
        if (oldParentId != NOT_EXIST && oldParentId != parentId) {
            throwException2(oldParentId, parentId, childId);
        }
        if (alreadyHasRelationship(child, parent)) {
            throwException3(parentId, childId);
        }
    }

    /**
     * 这两个节点,在树中是否已经拥有父子关系.
     *
     * @param parent parent
     * @param child  child
     * @return true-已经拥有正确的父子关系
     */
    private boolean alreadyHasRelationship(E parent, E child) {
        long oldParentId = id2PidMap.getOrDefault(child.getId(), NOT_EXIST);
        return oldParentId != NOT_EXIST && oldParentId == parent.getId();
    }

    /**
     * 节点添加到树中.
     *
     * @param node node
     */
    private void addNode(E node) {
        validateNode(node);
        id2NodeMap.put(node.getId(), node);
    }

    /**
     * Perform a depth-first traversal through this node and its descendants
     *
     * @param fromId   开始节点ID
     * @param consumer 对遍历过程中的每个节点执行的动作
     */
    public void traverse(long fromId, Consumer<E> consumer) {
        E node = get(fromId);
        if (node == null) {
            return;
        }
        consumer.accept(node);
        long[] children = getChildren(fromId);
        if (children == null || children.length == 0) {
            return;
        }
        for (long child : children) {
            traverse(child, consumer);
        }
    }
}
