package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.LongCollection;
import com.koloboke.collect.map.hash.*;
import com.koloboke.collect.set.hash.HashLongSets;
import com.wuda.yhan.util.commons.unique.LongIdObject;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 用Map的方式实现树形关系.树中元素都有一个唯一ID，并且是<code>long</code>类型.
 *
 * @param <E> 树中元素的类型
 * @author wuda
 */
public class LongIdMapTree<E extends LongIdObject> extends AbstractNumberIdMapTree {

    /**
     * 根元素.
     */
    private E root;

    /**
     * 保存关系,子元素指向父元素.key - id ,value - parent id.
     */
    private HashLongLongMap id2PidMap;
    /**
     * 保存关系,父元素指向它的所有字元素.
     * key - parent id , value - 所有的子元素ID的集合.
     */
    private HashLongObjMap<LongCollection> pid2ChildrenMap;
    /**
     * 保存数据.key - id , value - 此id对应的元素.
     */
    private HashLongObjMap<E> id2ElementMap;
    /**
     * 保存数据.key - id , value - 此id对应的元素的深度.
     */
    private HashLongIntMap id2DepthMap;

    /**
     * 如果Element ID等于改值,表示此ID不存在.
     */
    public final long NOT_EXIST = Long.MIN_VALUE;

    /**
     * 构造树.
     *
     * @param root     根元素
     * @param recDepth 是否记录元素的深度,如果记录的话,多使用一些内存
     */
    public LongIdMapTree(E root, boolean recDepth) {
        validateElement(root);
        this.root = root;
        this.recDepth = recDepth;
        init();
        addElement(root);
        setDepth(root);
    }

    /**
     * 构造树.不记录元素深度.
     *
     * @param root 根元素
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
        id2ElementMap = HashLongObjMaps.newMutableMap();
        if (recDepth) {
            id2DepthMap = HashLongIntMaps.newMutableMap();
        }
    }

    /**
     * 为两个元素建立父子关系.
     *
     * @param parent 作为父元素
     * @param child  作为子元素
     */
    public void createRelationship(E parent, E child) {
        validateElement(parent);
        validateElement(child);
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

        addElement(parent);
        addElement(child);
    }

    /**
     * 设置元素的深度.元素的深度等于它父元素深度加一.如果当前元素的深度发生变化,
     * 那么它的所有子元素的深度也会相应的更新.
     *
     * @param element element
     */
    private void setDepth(E element) {
        if (recDepth) {
            int depth = 0;
            long id = element.getId();
            if (id != root.getId()) {
                long pid = getParent(id);
                int parentDepth = getDepth(pid);
                depth = parentDepth + 1;
            }
            if (depth != getDepth(id)) {
                // 深度发生变化才更新
                id2DepthMap.put(id, depth);
                // 更新当前元素下的所有子元素的深度
                long[] children = getChildren(id);
                if (children != null && children.length > 0) {
                    for (long child : children) setDepth(get(child));
                }
            }
        }
    }

    /**
     * 获取根元素.
     *
     * @return root
     */
    public E getRoot() {
        return root;
    }

    /**
     * 获取给定id所代表的元素的所有子元素id.
     *
     * @param id 元素id
     * @return 这个元素id下的所有子元素
     */
    public long[] getChildren(long id) {
        LongCollection children = pid2ChildrenMap.get(id);
        if (children == null) {
            return null;
        }
        return children.toLongArray();
    }

    /**
     * 获取给定元素的深度.
     *
     * @param id 元素id
     * @return 深度, 如果{@link #recDepth}设置为<code>true</code>才记录深度
     */
    public int getDepth(long id) {
        if (!recDepth) {
            return NO_DEPTH;
        }
        return id2DepthMap.get(id);
    }

    /**
     * 获取ID对应的元素.
     *
     * @param id id
     * @return element
     */
    public E get(long id) {
        return id2ElementMap.get(id);
    }

    /**
     * 获取元素的祖先.
     *
     * @param id    element id
     * @param count 查找祖先的个数
     * @return 数组中第0个元素是直接父元素, 第一个元素是父元素的父元素, 依次类推.
     * 数组中的元素, 不一定都是该元素的祖先的ID,
     * 比如有个元素总共只有一个上级元素,但是你并不知道这个情况,你想取回它的三个祖先,
     * 在这种情况下,第0个元素是父元素ID,第一和第二个元素的值就是{@link #NOT_EXIST}
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
     * 获取直接父元素.
     *
     * @param id element id
     * @return 父元素, 如果不存在则返回{@link #NOT_EXIST}
     */
    public long getParent(Long id) {
        return id2PidMap.getOrDefault(id.longValue(), NOT_EXIST);
    }

    /**
     * 校验元素.
     *
     * @param element element
     */
    private void validateElement(E element) {
        if (element.getId() == NOT_EXIST) {
            throwException0(NOT_EXIST);
        }
    }

    /**
     * 验证两个元素的关系.
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
     * 这两个元素,在树中是否已经拥有父子关系.
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
     * 元素添加到树中.
     *
     * @param element element
     */
    private void addElement(E element) {
        validateElement(element);
        id2ElementMap.put(element.getId(), element);
    }

    /**
     * Perform a depth-first traversal through this element and its descendants
     *
     * @param fromId   开始元素ID
     * @param consumer 对遍历过程中的每个元素执行的动作
     */
    public void traverse(long fromId, Consumer<E> consumer) {
        E element = get(fromId);
        if (element == null) {
            return;
        }
        consumer.accept(element);
        long[] children = getChildren(fromId);
        if (children == null || children.length == 0) {
            return;
        }
        for (long child : children) {
            traverse(child, consumer);
        }
    }

    /**
     * Dumps an {@link LongIdMapTree} to a GraphViz's <code>dot</code> language description
     * for visualization.
     * <p>
     * Note: larger FSM (a few thousand nodes) won't even
     * render, don't bother.
     *
     * @param nodeLabelExtractor 提取元素的属性作为node label
     * @see <a href="http://www.graphviz.org/">graphviz project</a>
     */
    public <R> String toDot(Function<E, R> nodeLabelExtractor) {
        StringBuilder appender = new StringBuilder("digraph {");
        appender.append("node [fontname=\"FangSong\" size=\"20,20\"];");
        long id = root.getId();
        parentPointChild(appender, id, nodeLabelExtractor);
        appender.append("}");
        return appender.toString();
    }

    private <R> void parentPointChild(StringBuilder appender, long pid, Function<E, R> nodeLabelExtractor) {
        long[] children = getChildren(pid);
        if (children == null || children.length == 0) {
            return;
        }
        E parentE = get(pid);
        for (long child : children) {
            E childE = get(child);
            appender.append(nodeLabelExtractor.apply(parentE))
                    .append("->")
                    .append(nodeLabelExtractor.apply(childE))
                    .append(";");
            parentPointChild(appender, child, nodeLabelExtractor);
        }
    }
}
