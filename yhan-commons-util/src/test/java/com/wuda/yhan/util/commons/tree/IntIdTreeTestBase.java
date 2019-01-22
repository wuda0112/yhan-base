package com.wuda.yhan.util.commons.tree;

import com.wuda.yhan.util.commons.unique.IntIdPidObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

class IntIdTreeTestBase {

    int rootId = Integer.MIN_VALUE;

    IntIdPidElement china = new IntIdPidElement(1, rootId, "中国");

    IntIdPidElement hunan_province = new IntIdPidElement(2, 1, "湖南省");
    IntIdPidElement zhangjj = new IntIdPidElement(3, 2, "张家界市");
    IntIdPidElement changsha = new IntIdPidElement(4, 2, "长沙市");
    IntIdPidElement sangzhi = new IntIdPidElement(5, 3, "桑植县");

    IntIdPidElement guangdong_province = new IntIdPidElement(7, 1, "广东省");
    IntIdPidElement guangzhou = new IntIdPidElement(8, 7, "广州市");
    IntIdPidElement tianhe = new IntIdPidElement(12, 8, "天河区");
    IntIdPidElement dongguan = new IntIdPidElement(9, 7, "东莞市");

    IntIdPidElement hubei_province = new IntIdPidElement(10, 1, "湖北省");
    IntIdPidElement yunan_province = new IntIdPidElement(11, 1, "云南省");

    @Getter
    @Setter
    static class IntIdPidElement extends IntIdPidObject {

        String name;

        IntIdPidElement(int id, int pid, String name) {
            super(id, pid);
            this.name = name;
        }
    }

    /**
     * 遍历tree.
     *
     * @param tree
     *         tree
     */
    void display(RelationshipTree<IntIdPidElement> tree) {
        List<RelationshipTree.Node<IntIdPidElement>> nodes = tree.dfs(tree.getRoot());
        for (RelationshipTree.Node<IntIdPidElement> node : nodes) {
            int depth = node.getDepth();
            String s = "";
            for (int i = 0; i < depth; i++) {
                s += "  ";
            }
            if (node != tree.getRoot()) {
                System.out.println(s + node.getElement().getName());
            }
        }
    }
}
