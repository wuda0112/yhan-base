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
}
