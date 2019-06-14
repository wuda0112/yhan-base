package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.map.hash.HashIntObjMap;
import com.koloboke.collect.map.hash.HashIntObjMaps;
import com.wuda.yhan.util.commons.unique.IntIdPidObject;

import java.util.List;
import java.util.logging.Logger;

/**
 * 将具有ID/PID模式的数据生成{@link IntIdMapTree}树形结构.
 * 因为ID/PID描述了上下级关系,因此根据这个特点可以很容易的生成树形结构.
 *
 * @author wuda
 */
public class IntIdPidObjectMapTreeBuilder extends AbstractNumberIdMapTreeBuilder {

    /**
     * logger.
     */
    private static Logger logger = Logger.getLogger(IntIdPidObjectMapTreeBuilder.class.getName());

    /**
     * 向已经存在的{@link IntIdMapTree}中添加新的数据.
     *
     * @param tree     已经存在的{@link IntIdMapTree}
     * @param elements 新的元素,如果是分多次将元素添加到树中,
     *                 对于同一颗子树,处于上级的元素必须优先添加到树中,否则不能正确的建立父子关系.
     *                 这也是符合逻辑的,比如正常添加数据肯定也是先有父级,然后在父级下添加子元素.
     */
    public <E extends IntIdPidObject> void add(IntIdMapTree<E> tree, List<E> elements) {
        if (elements == null || elements.isEmpty()) {
            return;
        }
        HashIntObjMap<E> map = groupingById(elements);
        for (E element : elements) {
            int id = element.getId();
            if (id == tree.getRoot().getId()) {
                // 当前element是root,不需要建立关系
                continue;
            }
            int pid = element.getPid();
            E parent = getFrom(map, tree, pid);
            if (parent != null) {
                tree.createRelationship(parent, element);
            } else {
                String message = getMessage(id, pid);
                logger.warning(message);
            }
        }
    }

    /**
     * 从给定的两个容器中查找ID对应的元素.
     *
     * @param map  map
     * @param tree tree
     * @param id   元素ID
     * @param <E>  元素类型
     * @return element
     */
    private <E extends IntIdPidObject> E getFrom(HashIntObjMap<E> map, IntIdMapTree<E> tree, int id) {
        E node = map.get(id);
        if (node == null) {
            node = tree.get(id);
        }
        return node;
    }

    /**
     * 根据ID分组,由于ID是唯一的,因此一个ID只对应一个对应的元素.
     *
     * @param elements list of element
     * @param <E>      元素类型
     * @return key - id , value - element
     */
    private <E extends IntIdPidObject> HashIntObjMap<E> groupingById(List<E> elements) {
        int size = elements.size();
        HashIntObjMap<E> map = HashIntObjMaps.newMutableMap(size);
        for (E e : elements) {
            map.put(e.getId(), e);
        }
        return map;
    }
}
