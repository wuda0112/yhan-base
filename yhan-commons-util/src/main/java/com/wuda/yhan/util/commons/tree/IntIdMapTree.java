package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.IntCollection;
import com.koloboke.collect.map.hash.HashIntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.koloboke.collect.set.hash.HashIntSets;
import com.wuda.yhan.util.commons.unique.IntIdObject;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 用Map的方式实现树形关系.树中元素都有一个唯一ID，并且是<code>int</code>类型.
 *
 * @param <E> 树中元素的类型
 * @author wuda
 */
public class IntIdMapTree<E extends IntIdObject> extends AbstractNumberIdMapTree {

    /**
     * 根元素.
     */
    private E root;

    /**
     * 保存关系,子元素指向父元素.key - id ,value - parent id.
     */
    private HashIntIntMap id2PidMap;
    /**
     * 保存关系,父元素指向它的所有字元素.
     * key - parent id , value - 所有的子元素ID的集合.
     */
    private HashIntObjMap<IntCollection> pid2ChildrenMap;
    /**
     * 保存数据.key - id , value - 此id对应的元素.
     */
    private HashIntObjMap<E> id2ElementMap;
    /**
     * 保存数据.key - id , value - 此id对应的元素的深度.
     */
    private HashIntIntMap id2DepthMap;

    /**
     * 如果Element ID等于改值,表示此ID不存在.
     */
    public final int NOT_EXIST = Integer.MIN_VALUE;

    /**
     * 构造树.
     *
     * @param root     根元素
     * @param recDepth 是否记录元素的深度,如果记录的话,多使用一些内存
     */
    public IntIdMapTree(E root, boolean recDepth) {
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
    public IntIdMapTree(E root) {
        this(root, false);
    }

    /**
     * init.
     */
    private void init() {
        id2PidMap = HashIntIntMaps.newMutableMap();
        pid2ChildrenMap = HashIntObjMaps.newMutableMap();
        id2ElementMap = HashIntObjMaps.newMutableMap();
        if (recDepth) {
            id2DepthMap = HashIntIntMaps.newMutableMap();
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
        int childId = child.getId();
        int parentId = parent.getId();

        id2PidMap.put(childId, parentId);
        IntCollection children = pid2ChildrenMap.get(parentId);
        if (children == null) {
            children = HashIntSets.newMutableSet();
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
            int id = element.getId();
            if (id != root.getId()) {
                int pid = getParent(id);
                int parentDepth = getDepth(pid);
                depth = parentDepth + 1;
            }
            if (depth != getDepth(id)) {
                // 深度发生变化才更新
                id2DepthMap.put(id, depth);
                // 更新当前元素下的所有子元素的深度
                int[] children = getChildren(id);
                if (children != null && children.length > 0) {
                    for (int child : children) setDepth(get(child));
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
    public int[] getChildren(int id) {
        IntCollection children = pid2ChildrenMap.get(id);
        if (children == null) {
            return null;
        }
        return children.toIntArray();
    }

    /**
     * 获取给定元素的深度.
     *
     * @param id 元素id
     * @return 深度, 如果{@link #recDepth}设置为<code>true</code>才记录深度
     */
    public int getDepth(int id) {
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
    public E get(int id) {
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
    public int[] getAncestor(int id, int count) {
        int[] ancestors = initArray(count, NOT_EXIST);
        if (ancestors == null) {
            return null;
        }
        int index = 0;
        while (index < count) {
            int pid = getParent(id);
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
    private int[] initArray(int capacity, int initValue) {
        if (capacity <= 0) {
            return null;
        }
        int[] array = new int[capacity];
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
    public int getParent(int id) {
        return id2PidMap.getOrDefault(id, NOT_EXIST);
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
        int childId = child.getId();
        if (childId == root.getId()) {
            throwException1(childId, parent.getId());
        }
        int parentId = parent.getId();
        int oldParentId = id2PidMap.getOrDefault(childId, NOT_EXIST);
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
        int oldParentId = id2PidMap.getOrDefault(child.getId(), NOT_EXIST);
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
    public void traverse(int fromId, Consumer<E> consumer) {
        E element = get(fromId);
        if (element == null) {
            return;
        }
        consumer.accept(element);
        int[] children = getChildren(fromId);
        if (children == null || children.length == 0) {
            return;
        }
        for (int child : children) {
            traverse(child, consumer);
        }
    }

    /**
     * Dumps an {@link IntIdMapTree} to a GraphViz's <code>dot</code> language description
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
        int id = root.getId();
        parentPointChild(appender, id, nodeLabelExtractor);
        appender.append("}");
        return appender.toString();
    }

    private <R> void parentPointChild(StringBuilder appender, int pid, Function<E, R> nodeLabelExtractor) {
        int[] children = getChildren(pid);
        if (children == null || children.length == 0) {
            return;
        }
        E parentE = get(pid);
        for (int child : children) {
            E childE = get(child);
            appender.append(nodeLabelExtractor.apply(parentE))
                    .append("->")
                    .append(nodeLabelExtractor.apply(childE))
                    .append(";");
            parentPointChild(appender, child, nodeLabelExtractor);
        }
    }
}
