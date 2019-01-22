package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class IntIdPidObjectTreeBuilderTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        IntIdPidObjectTreeBuilder<IntIdPidElement> builder = new IntIdPidObjectTreeBuilder<>();
        IntIdRelationshipTree<IntIdPidElement> tree = new IntIdRelationshipTree<>(rootId);
        List<IntIdPidElement> elements = getElements();
        builder.add(tree, elements);
        builder.add(tree, tianhe);
        display(tree); // 遍历并且打印
    }

    private List<IntIdPidElement> getElements() {
        List<IntIdPidElement> nodes = new ArrayList<>();
        nodes.add(china);
        nodes.add(hunan_province);
        nodes.add(zhangjj);
        nodes.add(changsha);
        nodes.add(sangzhi);
        nodes.add(guangdong_province);
        nodes.add(guangzhou);
        nodes.add(dongguan);
        nodes.add(hubei_province);
        nodes.add(yunan_province);
        return nodes;
    }
}
