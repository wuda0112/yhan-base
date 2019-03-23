package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.wuda.yhan.util.commons.unique.IntIdPidObject;

import java.util.List;

public class IntIdPidObjectMapTreeBuilder {

    /**
     * 向已经存在的{@link IntIdMapTree}中添加新的数据.
     *
     * @param tree     已经存在的{@link IntIdMapTree}
     * @param elements 新的元素
     */
    public <E extends IntIdPidObject> void add(IntIdMapTree<E> tree, List<E> elements) {
        if (elements == null || elements.isEmpty()) {
            return;
        }
        HashIntObjMap<E> map = groupingById(elements);
        for (E e : elements) {
            int id = e.getId();
            int pid = e.getPid();
            E child = map.get(id);
            if (child == null) {
                child = tree.get(id);
            }
            E parent = map.get(pid);
            if (parent == null) {
                parent = tree.get(pid);
            }
            if (parent != null && child != null) {
                tree.createRelationship(parent, child);
            } else {
                // todo log
            }
        }
    }

    private <E extends IntIdPidObject> HashIntObjMap<E> groupingById(List<E> elements) {
        int size = elements.size();
        HashIntObjMap<E> map = HashIntObjMaps.newMutableMap(size);
        for (E e : elements) {
            map.put(e.getId(), e);
        }
        return map;
    }
}
