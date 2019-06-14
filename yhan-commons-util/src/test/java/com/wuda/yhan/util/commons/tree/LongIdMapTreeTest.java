package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.Arrays;

public class LongIdMapTreeTest extends LongIdTreeTestBase {

    /**
     * 测试父子关系互换的情形.
     */
    @Test
    public void testSwitchRelationship() {
        LongIdMapTree<LongIdPidElement> tree = new LongIdMapTree<>(china);
        LongIdTreeTestBase.LongIdPidElement parent = guangdong_province;
        LongIdTreeTestBase.LongIdPidElement child = guangzhou;

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
        LongIdMapTree<LongIdPidElement> tree = new LongIdMapTree<>(china);
        LongIdPidElement parent = guangdong_province;
        LongIdPidElement child = guangzhou;
        LongIdPidElement fakeParent = hunan_province;
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
        LongIdMapTree<LongIdPidElement> tree = getTree();
        LongIdPidElement root = tree.getRoot();
        print(tree, root.getId());
    }

    @Test
    public void getAncestorsTest() {
        LongIdMapTree<LongIdPidElement> tree = getTree();

        LongIdPidElement root = tree.getRoot();
        long[] ancestors = tree.getAncestor(root.getId(), 3);
        System.out.println(Arrays.toString(ancestors));

        long[] zjjAncestors = tree.getAncestor(zhangjj.getId(), 4);
        System.out.println(Arrays.toString(zjjAncestors));
    }

    /**
     * 测试正常情形.
     */
    @Test
    public void testDepth() {
        LongIdMapTree<LongIdPidElement> tree = new LongIdMapTree<>(china, true);

        // 从下往上建立关系
        tree.createRelationship(zhangjj, sangzhi);
        tree.createRelationship(hunan_province, zhangjj);
        tree.createRelationship(china, hunan_province);

        tree.createRelationship(guangdong_province, guangzhou);
        tree.createRelationship(china, guangdong_province);


        LongIdPidElement root = tree.getRoot();
        print(tree, root.getId());
    }

    private LongIdMapTree<LongIdPidElement> getTree() {

        LongIdMapTree<LongIdPidElement> tree = new LongIdMapTree<>(china, true);

        tree.createRelationship(china, guangdong_province);
        tree.createRelationship(guangdong_province, guangzhou);

        tree.createRelationship(china, hunan_province);
        tree.createRelationship(hunan_province, zhangjj);

        return tree;
    }

    @Test
    public void toDot() {
        System.out.println(getTree().toDot(LongIdPidElement::getName));
    }

}
