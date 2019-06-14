package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.List;

public class IntIdPidObjectMapTreeBuilderTest extends IntIdTreeTestBase {

    @Test
    public void test() {
        List<IntIdPidElement> elements = getElements();
        IntIdMapTree<IntIdPidElement> tree = new IntIdMapTree<>(china, true);
        IntIdPidObjectMapTreeBuilder builder = new IntIdPidObjectMapTreeBuilder();
        builder.add(tree, elements);
        print(tree, china.getId());
        System.out.println(tree.toDot(IntIdPidElement::getName));
    }
}
