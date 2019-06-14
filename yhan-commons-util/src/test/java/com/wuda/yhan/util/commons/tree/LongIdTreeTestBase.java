package com.wuda.yhan.util.commons.tree;

import com.wuda.yhan.util.commons.unique.LongIdPidObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

class LongIdTreeTestBase {

    LongIdPidElement china = new LongIdPidElement(1, -1, "中国");

    LongIdPidElement hunan_province = new LongIdPidElement(2, china.getId(), "湖南省");
    LongIdPidElement zhangjj = new LongIdPidElement(3, hunan_province.getId(), "张家界市");
    LongIdPidElement changsha = new LongIdPidElement(4, hunan_province.getId(), "长沙市");
    LongIdPidElement sangzhi = new LongIdPidElement(5, zhangjj.getId(), "桑植县");

    LongIdPidElement guangdong_province = new LongIdPidElement(7, china.getId(), "广东省");
    LongIdPidElement guangzhou = new LongIdPidElement(8, guangdong_province.getId(), "广州市");
    LongIdPidElement tianhe = new LongIdPidElement(12, guangzhou.getId(), "天河区");
    LongIdPidElement dongguan = new LongIdPidElement(9, guangdong_province.getId(), "东莞市");

    LongIdPidElement hubei_province = new LongIdPidElement(10, china.getId(), "湖北省");
    LongIdPidElement yunan_province = new LongIdPidElement(11, china.getId(), "云南省");

    public List<LongIdPidElement> getElements() {
        List<LongIdPidElement> list = new ArrayList<>();
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
    static class LongIdPidElement extends LongIdPidObject {

        String name;

        LongIdPidElement(long id, long pid, String name) {
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
    void print(LongIdMapTree<LongIdPidElement> tree, long id) {
        int depth = tree.getDepth(id);
        String tab = "\t";
        for (int i = 0; i < depth; i++) {
            tab += "\t";
        }
        LongIdPidElement element = tree.get(id);
        System.out.println(tab + element.getName());
        long[] children = tree.getChildren(id);
        if (children == null || children.length == 0) {
            return;
        }
        for (long child : children) {
            print(tree, child);
        }
    }
}
