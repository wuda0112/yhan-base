package com.wuda.yhan.util.commons.tree;

import com.wuda.yhan.util.commons.unique.NumberIdPidObject;

import java.util.List;

/**
 * 将具有ID/PID模式的数据生成{@link RelationshipTree}树形结构.
 * 因为ID/PID描述了上下级关系,因此根据这个特点可以很容易的生成树形结构.生成树时有以下一些规则
 * <ul>
 * <li>如果同一个id被多次添加,则后添加的将覆盖前面添加的</li>
 * <li>
 * 如果同一个id被多次添加,并且它们具有不同的pid,则此上下级关系以最后一次的ID/PID为准,并且以此id为父级(祖先)的所有下级都跟着一起改变了,
 * 对这句话的理解可以参考一下XMind,FreeMind软件把A节点从原来的B节点下拖到C节点下的结果</li>
 * <li>如果分多次将数据添加到树中,则必须根据树形结构从上到下添加数据,不然无法正常建立父子关系,
 * 但是对于同一次{@link #add(RelationshipTree, List)}调用,list中的元素可以是无序的.
 * 举例,有以下树形结构
 * <pre>
 *     A
 *      A1
 *       A1.1
 *     B
 *      B1
 *       B1.1
 * </pre>
 * 总共有6个节点,如果你分6次添加到树中,则必须先添加A,然后添加A1,然后添加A1.1 .
 * 当然A*节点和B*节点之间不分先后.但是如果你一次性全部添加,则可以无序,即使[B1.1,A1,A1.1,B,A,B1]
 * 也能正常建立关系.
 * </li>
 * </ul>
 *
 * @param <T>
 *         用于生成树的数据的类型,同时也是生成的{@link RelationshipTree.Node#getElement()}的数据类型
 * @author wuda
 */
public interface IdPidObjectTreeBuilder<T extends NumberIdPidObject> {

    /**
     * 向已经存在的{@link RelationshipTree}中添加新的数据.
     *
     * @param tree
     *         已经存在的{@link RelationshipTree}
     * @param elements
     *         新的元素
     */
    void add(RelationshipTree<T> tree, List<T> elements);

    /**
     * 向已经存在的{@link RelationshipTree}中添加新的数据.
     *
     * @param tree
     *         已经存在的{@link RelationshipTree}
     * @param element
     *         新的元素
     */
    void add(RelationshipTree<T> tree, T element);

}
