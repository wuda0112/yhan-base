package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.List;

public class LongIdPidObjectMapTreeBuilderTest extends LongIdTreeTestBase {

    @Test
    public void test() {
        List<LongIdPidElement> elements = getElements();
        LongIdMapTree<LongIdPidElement> tree = new LongIdMapTree<>(china, true);
        LongIdPidObjectMapTreeBuilder builder = new LongIdPidObjectMapTreeBuilder();
        builder.add(tree, elements);
        print(tree, china.getId());
        System.out.println(tree.toDot(LongIdPidElement::getName));
    }
}
