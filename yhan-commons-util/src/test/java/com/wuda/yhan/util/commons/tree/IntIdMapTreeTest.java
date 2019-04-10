package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.Arrays;

public class IntIdMapTreeTest extends IntIdTreeTestBase {

    /**
     * 测试父子关系互换的情形.
     */
    @Test
    public void testSwitchRelationship() {
        IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> tree = new IntIdMapTree<>(china);
        IntIdTreeTestBase.IntIdPidElement parent = guangdong_province;
        IntIdTreeTestBase.IntIdPidElement child = guangzhou;

        try {
            tree.createRelationship(parent, child);
            tree.createRelationship(child, parent);
        } catch (Exception e) {
            // 测试的是有异常的情形
            e.printStackTrace();
        }
    }

    /**
     * 测试子节点有多个父节点的情形.
     */
    @Test
    public void testChildHasMultiParent() {
        IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> tree = new IntIdMapTree<>(china);
        IntIdTreeTestBase.IntIdPidElement parent = guangdong_province;
        IntIdTreeTestBase.IntIdPidElement child = guangzhou;
        IntIdTreeTestBase.IntIdPidElement fakeParent = hunan_province;
        try {
            tree.createRelationship(parent, child);
            tree.createRelationship(fakeParent, child);
        } catch (Exception e) {
            // 测试的是有异常的情形
            e.printStackTrace();
        }
    }

    /**
     * 测试正常情形.
     */
    @Test
    public void test() {
        IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> tree = getTree();
        IntIdTreeTestBase.IntIdPidElement root = tree.getRoot();
        print(tree, root.getId());
    }

    @Test
    public void getAncestorsTest() {
        IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> tree = getTree();

        IntIdTreeTestBase.IntIdPidElement root = tree.getRoot();
        int[] ancestors = tree.getAncestor(root.getId(), 3);
        System.out.println(Arrays.toString(ancestors));

        int[] zjjAncestors = tree.getAncestor(zhangjj.getId(), 4);
        System.out.println(Arrays.toString(zjjAncestors));
    }

    /**
     * 测试正常情形.
     */
    @Test
    public void testDepth() {
        IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> tree = new IntIdMapTree<>(china, true);

        // 从下往上建立关系
        tree.createRelationship(zhangjj, sangzhi);
        tree.createRelationship(hunan_province, zhangjj);
        tree.createRelationship(china, hunan_province);

        tree.createRelationship(guangdong_province, guangzhou);
        tree.createRelationship(china, guangdong_province);


        IntIdTreeTestBase.IntIdPidElement root = tree.getRoot();
        print(tree, root.getId());
    }

    private IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> getTree() {

        IntIdMapTree<IntIdTreeTestBase.IntIdPidElement> tree = new IntIdMapTree<>(china, true);

        tree.createRelationship(china, guangdong_province);
        tree.createRelationship(guangdong_province, guangzhou);

        tree.createRelationship(china, hunan_province);
        tree.createRelationship(hunan_province, zhangjj);

        return tree;
    }

}
