package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.Arrays;

public class IntIdMapTreeTest extends IntIdTreeTestBase{

    IntIdTreeTestBase.IntIdElement root = new IntIdTreeTestBase.IntIdElement(-1, "root");
    IntIdTreeTestBase.IntIdElement hn = new IntIdTreeTestBase.IntIdElement(1, "湖南");
    IntIdTreeTestBase.IntIdElement zjj = new IntIdTreeTestBase.IntIdElement(2, "张家界");
    IntIdTreeTestBase.IntIdElement gd = new IntIdTreeTestBase.IntIdElement(3, "广东");
    IntIdTreeTestBase.IntIdElement gz = new IntIdTreeTestBase.IntIdElement(4, "广州");

    /**
     * 测试父子关系互换的情形.
     */
    @Test
    public void testSwitchRelationship() {
        IntIdTreeTestBase.IntIdElement root = new IntIdTreeTestBase.IntIdElement(-1, "root");
        IntIdMapTree<IntIdTreeTestBase.IntIdElement> tree = new IntIdMapTree<>(root);
        IntIdTreeTestBase.IntIdElement parent = new IntIdTreeTestBase.IntIdElement(1, "parent");
        IntIdTreeTestBase.IntIdElement child = new IntIdTreeTestBase.IntIdElement(2, "child");

        tree.createRelationship(parent, child);
        tree.createRelationship(child, parent);
    }

    /**
     * 测试子节点有多个父节点的情形.
     */
    @Test
    public void testChildHasMultiParent() {
        IntIdTreeTestBase.IntIdElement root = new IntIdTreeTestBase.IntIdElement(-1, "root");
        IntIdMapTree<IntIdTreeTestBase.IntIdElement> tree = new IntIdMapTree<>(root);
        IntIdTreeTestBase.IntIdElement parent = new IntIdTreeTestBase.IntIdElement(1, "parent");
        IntIdTreeTestBase.IntIdElement child = new IntIdTreeTestBase.IntIdElement(2, "child");
        IntIdTreeTestBase.IntIdElement fakeParent = new IntIdTreeTestBase.IntIdElement(3, "fakeParent");

        tree.createRelationship(parent, child);
        tree.createRelationship(fakeParent, child);
    }

    /**
     * 测试正常情形.
     */
    @Test
    public void test() {
        IntIdMapTree<IntIdTreeTestBase.IntIdElement> tree = getTree();
        IntIdTreeTestBase.IntIdElement root = tree.getRoot();
        print(tree, root.getId());
    }

    @Test
    public void getAncestorsTest() {
        IntIdMapTree<IntIdTreeTestBase.IntIdElement> tree = getTree();

        IntIdTreeTestBase.IntIdElement root = tree.getRoot();
        int[] ancestors = tree.getAncestor(root.getId(), 3);
        System.out.println(Arrays.toString(ancestors));

        int[] zjjAncestors = tree.getAncestor(zjj.getId(), 4);
        System.out.println(Arrays.toString(zjjAncestors));
    }

    private IntIdMapTree<IntIdTreeTestBase.IntIdElement> getTree() {

        IntIdMapTree<IntIdTreeTestBase.IntIdElement> tree = new IntIdMapTree<>(root, true);

        tree.createRelationship(root, hn);
        tree.createRelationship(hn, zjj);

        tree.createRelationship(root, gd);
        tree.createRelationship(gd, gz);

        return tree;
    }

}
