package com.wuda.yhan.util.commons.tree;

import com.wuda.yhan.util.commons.unique.NumberIdObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 树型结构,所包含的数据都必须具有唯一id.不是search tree,因此在性能上肯定比不上专门用于查找功能的树. 此数据结构更加注重表示树形关系.
 * first-child next-sibling方式的实现.一些大的原则如下
 * <ul>
 * <li>由于节点的元素被限制为{@link NumberIdObject},因此树中的每一个节点也跟着拥有了一个唯一id,id的值就等于它包含的元素的id</li>
 * <li>id代表的意思就是唯一,因此树中不可能有两个节点有相同的id</li>
 * <li>如果同一个id被多次使用在具有修改行为的方法上,则总是后面的覆盖前面</li>
 * <li>根据id删除(remove)时,当前id所在的节点和它的所有子节点都被删除,可以参考XMind,FreeMind的删除情形</li>
 * </ul>
 *
 * @param <T>
 *         节点中包含的元素类型
 * @author wuda
 */
public abstract class RelationshipTree<T extends NumberIdObject> {

    /**
     * 树的根节点.
     */
    protected final Node<T> root = new Node<>(null);

    /**
     * 由于每个<i>element</i>都有唯一id,因此如果当id已经存在树中,则更新id所在节点的数据,如果不存在,则创建一个新的节点.
     * 当新创建节点后,节点被保存到树中,但是此刻它没有与树中其他节点关联,是一个孤立的节点.调用{@link #createRelationship(Node, Node)}和其他节点建立联系.
     *
     * @param element
     *         节点的元素
     * @return node
     */
    public Node<T> updateOrCreateNode(T element) {
        if (element == null) {
            throw new NullPointerException("element 为null");
        }
        Node<T> node = updateById(element);
        if (node != null) {
            return node;
        }
        node = new Node<>(element);
        putToContainer(node);
        return node;
    }

    /**
     * 为两个节点创建父子关系.
     * <ul>
     * <li>如果传入的子节点目前还没有父节点,则正常的为它们创建父子关系即可</li>
     * <li>如果传入的子节点已经有父节点,并且它的父节等于当前传入的父节点参数,则不做任何处理</li>
     * <li>如果传入的子节点已经有父节点,并且它的父节点不是当前传入的父节点参数,那么这个子节点的父级将调整为当前传入的父节点,
     * 并且此子节点下的所有下级都跟着一起改变了,对这句话的理解可以参考一下XMind,FreeMind软件把A节点从原来的B节点下拖到C节点下的结果</li>
     * <li>如果他们之间有父子关系,但是现在是互相调换,即原来的父节点变成了子节点,原来的子节点变成了父节点,
     * 这种情况无法处理,原因是:原来的父节点的其他子节点该如何处理呢?如果真的有这种情况,那么只能重新生成树了.
     * 具体检查由{@link #relationshipSwitch(Node, Node)}完成
     * </li>
     * </ul>
     *
     * @param parent
     *         父节点
     * @param child
     *         子节点
     * @throws AlreadyHasParentException
     *         子节点已经拥有父节点
     */
    public void createRelationship(Node<T> parent, Node<T> child) throws AlreadyHasParentException {
        if (parent == null) {
            throw new NullPointerException("父节点不能为空");
        }
        if (child.parent != null && !child.parent.equals(parent)) {
            throw new AlreadyHasParentException("child 已经拥有了一个父节点,并且这个父节点不是当前提供的父节点");
        }
        Node<T> oldChild = find(parent, child.element);
        if (oldChild != null) {
            // child已经存在,则后面的覆盖前面
            oldChild.element = child.element;
            return;
        }
        if (parent.firstChild == null) {
            parent.firstChild = child;
        } else {
            Node<T> sibling = parent.firstChild;
            while (sibling.nextSibling != null) {
                sibling = sibling.nextSibling;
            }
            sibling.nextSibling = child;
        }
        child.parent = parent;
    }

    /**
     * 检查两个节点的父子关系是否互换.比如在树中已经有两个节点NodeA和NodeB,
     * 并且NodeA的父节点是NodeB;现在有其他操作正准备把NodeB的父节点设置成NodeA,
     * 这是不允许的,因此必须首先检查一下两个节点是否互换了父子关系.
     * 在此方法中,参数<i>candidateChild</i>就好比是NodeB,参数<i>candidateParent</i>就好比是NodeA.
     * 还有一种理解方式是:现在有两个节点准备建立父子关系,在这个关系中,<i>candidateChild</i>是子节点,
     * <i>candidateParent</i>是父节点,帮忙检查下它们是否互换了父子关系.
     *
     * @param candidateChild
     *         这次检查中的子节点参数
     * @param candidateParent
     *         这次检查中的子节点参数
     * @return true-如果被检查的子节点和被检查的父节点,在已经存在的树中关系正好相反
     */
    boolean relationshipSwitch(Node<T> candidateChild, Node<T> candidateParent) {
        if (candidateChild == null || candidateParent == null) {
            return false;
        }
        return candidateParent.parent.equals(candidateChild);
    }

    /**
     * 寻找父节点下的指定元素的子节点.
     *
     * @param parent
     *         父节点
     * @param childElement
     *         子节点的元素
     * @return childElement所属的子节点, null-如果没有找到
     */
    @SuppressWarnings("unchecked")
    public Node<T> find(Node<T> parent, T childElement) {
        if (parent == null) {
            return null;
        }
        Node<T> child = parent.firstChild;
        while (child != null) {
            if (child.getElement().compareTo(childElement) == 0) {// 找到
                return child;
            }
            child = child.nextSibling;
        }
        return null;
    }

    /**
     * 深度优先遍历.数据量多的时候不能调用.
     *
     * @param start
     *         开始节点
     * @return 此节点的所有后裔, 包括此节点, 并且此节点一定是位于集合的第一个位置
     */
    public List<Node<T>> dfs(Node<T> start) {
        if (start == null) {
            return null;
        }
        LinkedList<Node<T>> backtracking = new LinkedList<>();
        List<Node<T>> nodes = new LinkedList<>();
        backtracking.addFirst(start);
        Node<T> current;
        while (!backtracking.isEmpty()) {
            current = backtracking.removeFirst();
            while (current != null) {
                if (current.nextSibling != null) {
                    backtracking.addFirst(current.nextSibling);
                }
                nodes.add(current);
                current = current.firstChild;
            }
        }
        return nodes;
    }

    /**
     * 获取树的根节点.
     *
     * @return the root
     */
    public Node<T> getRoot() {
        return root;
    }

    /**
     * 当调用{@link #updateOrCreateNode(NumberIdObject)}创建一个新的节点后,将此新节点放到容器中,以便当前这棵树能持有这个节点.
     * 至于这个容器是数组,list,map等等,都由子类具体决定.
     * 比如可以用Map当做容器,key是id,value是node,这样根据id查找和删除节点时都会快速完成.
     *
     * @param node
     *         node which contains id
     */
    protected abstract void putToContainer(Node<T> node);

    /**
     * 根据id获取节点.
     *
     * @param id
     *         id
     * @return node
     */
    public abstract Node<T> getById(Number id);

    /**
     * 根据id删除节点.当前节点以及它的所有子节点都从树中移除.
     *
     * @param id
     *         id
     * @return id所在的节点, 如果不存在则返回null
     */
    public abstract Node<T> removeById(Number id);

    /**
     * 根据id更新节点中的元素.
     *
     * @param element
     *         element which contains id field
     * @return 如果更新成功, 则返回更新后的节点, 如果此id所代表的元素(或者说节点)不存在, 则返回<code>null</code>
     */
    public abstract Node<T> updateById(T element);

    /**
     * 树的节点.
     *
     * @param <E>
     *         元素的类型
     * @author wuda
     */
    public static class Node<E extends NumberIdObject> {
        /**
         * 当前节点的第一个子节点.
         */
        private Node<E> firstChild = null;
        /**
         * 当前节点的兄弟节点.
         */
        private Node<E> nextSibling = null;
        /**
         * 当前节点的父节点.
         */
        Node<E> parent = null;
        /**
         * 节点中的数据元素.
         */
        E element;

        /**
         * 构建一个节点.
         *
         * @param element
         *         节点的元素.
         */
        Node(E element) {
            this.element = element;
        }

        @Override
        public String toString() {
            return element.toString();
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            if (obj instanceof Node) {
                Node<E> node = (Node<E>) obj;
                return this.element.compareTo(node.element) == 0;
            }
            return false;
        }

        /**
         * 获取节点的元素.
         *
         * @return the element
         */
        public E getElement() {
            return element;
        }

        /**
         * 获取节点的深度.
         *
         * @return the depth
         */
        public int getDepth() {
            int depth = 0;
            Node<E> parent = this.parent;
            while (parent != null) {
                depth++;
                parent = parent.parent;
            }
            return depth;
        }
    }
}
