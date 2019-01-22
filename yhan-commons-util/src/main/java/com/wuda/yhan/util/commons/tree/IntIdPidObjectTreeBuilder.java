package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.map.hash.HashIntIntMap;
import com.koloboke.collect.map.hash.HashIntIntMaps;
import com.koloboke.function.IntIntConsumer;
import com.wuda.yhan.util.commons.unique.IntIdPidObject;
import com.wuda.yhan.util.commons.unique.NumberIdPidObject;

import java.util.List;

/**
 * {@link IntIdRelationshipTree}builder.ID和PID是int类型.
 * {@inheritDoc}
 *
 * @author wuda
 */
public class IntIdPidObjectTreeBuilder<T extends IntIdPidObject & NumberIdPidObject> extends AbstractIdPidObjectTreeBuilder<T> {

    /**
     * 向已经存在的{@link IntIdRelationshipTree}中添加新的数据.
     *
     * @param tree
     *         已经存在的{@link IntIdRelationshipTree}
     * @param elements
     *         新的元素
     */
    public void add(IntIdRelationshipTree<T> tree, List<T> elements) {
        // key - ID ,value - PID,用于保存父子关系
        HashIntIntMap IDPIDMap = HashIntIntMaps.newMutableMap();
        // phase 1, 检查是否有父子关系是否正常
        elements.forEach(element -> {
            int id = element.getId();
            int pid = element.getPid();
            tree.checkRelationship(id, pid);// 在已经存在的树中检查
            checkRelationship(id, pid, IDPIDMap);// 在当前添加的数据中检查
            IDPIDMap.put(id, pid);
        });
        /*
         * phase 2, 创建节点
         */
        elements.forEach(tree::updateOrCreateNode);
        // phase 3, 为节点建立关系
        build(tree, IDPIDMap);
        IDPIDMap.clear();
    }

    /**
     * 向已经存在的{@link RelationshipTree}中添加新的数据.
     *
     * @param tree
     *         已经存在的{@link RelationshipTree}
     * @param element
     *         新的元素
     */
    public void add(IntIdRelationshipTree<T> tree, T element) {
        tree.checkRelationship(element.getId(), element.getPid());
        tree.updateOrCreateNode(element);
        tree.createRelationship(element.getId(), element.getPid());
    }

    private void checkRelationship(int id, int pid, HashIntIntMap IDPIDMap) {
        if (IDPIDMap.getOrDefault(pid, Integer.MIN_VALUE) == id) {
            throw new RuntimeException("在准备添加到树中的数据中,有父子关系互换的情况!它们的id是:" + id + "和" + pid);
        }
    }

    private void build(IntIdRelationshipTree<T> tree, HashIntIntMap IDPIDMap) {
        IDPIDMap.forEach((IntIntConsumer) tree::createRelationship);
    }
}
