package com.wuda.yhan.util.commons.tree;

import com.koloboke.collect.map.hash.HashLongObjMap;
import com.koloboke.collect.map.hash.HashLongObjMaps;
import com.wuda.yhan.util.commons.unique.LongIdPidObject;

import java.util.List;
import java.util.logging.Logger;

/**
 * 将具有ID/PID模式的数据生成{@link LongIdMapTree}树形结构.
 * 因为ID/PID描述了上下级关系,因此根据这个特点可以很容易的生成树形结构.
 *
 * @author wuda
 */
public class LongIdPidObjectMapTreeBuilder {

    /**
     * logger.
     */
    private static Logger logger = Logger.getLogger(LongIdPidObjectMapTreeBuilder.class.getName());

    /**
     * 向已经存在的{@link IntIdMapTree}中添加新的数据.
     *
     * @param tree     已经存在的{@link IntIdMapTree}
     * @param elements 新的元素,如果是分多次将元素添加到树中,
     *                 对于同一颗子树,处于上级的元素必须优先添加到树中,否则不能正确的建立父子关系.
     *                 这也是符合逻辑的,比如正常添加数据肯定也是先有父级,然后在父级下添加子元素.
     */
    public <E extends LongIdPidObject> void add(LongIdMapTree<E> tree, List<E> elements) {
        if (elements == null || elements.isEmpty()) {
            return;
        }
        HashLongObjMap<E> map = groupingById(elements);
        for (E element : elements) {
            long id = element.getId();
            if (id == tree.getRoot().getId()) {
                // 当前element是root,不需要建立关系
                continue;
            }
            long pid = element.getPid();
            E parent = getFrom(map, tree, pid);
            if (parent != null) {
                tree.createRelationship(parent, element);
            } else {
                String message = "id=" + id + ",pid=" + pid + ",不能建立父子关系."
                        + "因为父元素并不在给定的元素列表和树中."
                        + "一种可能的原因是:此父元素根本不存在,这可以检查你的数据即可验证."
                        + "最有可能的原因是:该父元素本身是存在的,但是在此之前并没有被添加到树中."
                        + "这种情况的解决方案是:对于所有元素,必须先将父级先添加到树中,然后再添加子元素";
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
    private <E extends LongIdPidObject> E getFrom(HashLongObjMap<E> map, LongIdMapTree<E> tree, long id) {
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
    private <E extends LongIdPidObject> HashLongObjMap<E> groupingById(List<E> elements) {
        int size = elements.size();
        HashLongObjMap<E> map = HashLongObjMaps.newMutableMap(size);
        for (E e : elements) {
            map.put(e.getId(), e);
        }
        return map;
    }
}
