package com.wuda.yhan.util.commons.tree;

import com.wuda.yhan.util.commons.unique.IntIdPidObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

class IntIdTreeTestBase {

    IntIdPidElement china = new IntIdPidElement(1, -1, "中国");

    IntIdPidElement hunan_province = new IntIdPidElement(2, china.getId(), "湖南省");
    IntIdPidElement zhangjj = new IntIdPidElement(3, hunan_province.getId(), "张家界市");
    IntIdPidElement changsha = new IntIdPidElement(4, hunan_province.getId(), "长沙市");
    IntIdPidElement sangzhi = new IntIdPidElement(5, zhangjj.getId(), "桑植县");

    IntIdPidElement guangdong_province = new IntIdPidElement(7, china.getId(), "广东省");
    IntIdPidElement guangzhou = new IntIdPidElement(8, guangdong_province.getId(), "广州市");
    IntIdPidElement tianhe = new IntIdPidElement(12, guangzhou.getId(), "天河区");
    IntIdPidElement dongguan = new IntIdPidElement(9, guangdong_province.getId(), "东莞市");

    IntIdPidElement hubei_province = new IntIdPidElement(10, china.getId(), "湖北省");
    IntIdPidElement yunan_province = new IntIdPidElement(11, china.getId(), "云南省");

    public List<IntIdPidElement> getElements() {
        List<IntIdPidElement> list = new ArrayList<>();
        list.add(china);
        list.add(hunan_province);
        list.add(zhangjj);
        list.add(changsha);
        list.add(sangzhi);
        list.add(guangdong_province);
        list.add(guangzhou);
        list.add(tianhe);
        list.add(dongguan);
        list.add(hubei_province);
        list.add(yunan_province);
        return list;
    }

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
     * 打印,从给定的ID所代表的节点开始.
     *
     * @param tree tree
     * @param id   element id
     */
    void print(IntIdMapTree<IntIdPidElement> tree, int id) {
        int depth = tree.getDepth(id);
        String tab = "\t";
        for (int i = 0; i < depth; i++) {
            tab += "\t";
        }
        IntIdPidElement element = tree.get(id);
        System.out.println(tab + element.getName());
        int[] children = tree.getChildren(id);
        if (children == null || children.length == 0) {
            return;
        }
        for (int child : children) {
            print(tree, child);
        }
    }
}
