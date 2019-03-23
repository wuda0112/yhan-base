package com.wuda.yhan.util.commons.tree;

import org.junit.Test;

import java.util.List;

public class IntIdPidObjectMapTreeBuilderTest extends IntIdTreeTestBase{

    @Test
    public void t(){
        List<IntIdPidElement> elements=getElements();
        IntIdPidElement root = new IntIdPidElement(rootId,-1,"root");
        IntIdMapTree<IntIdPidElement> tree = new IntIdMapTree<>(root,true);
        IntIdPidObjectMapTreeBuilder builder=new IntIdPidObjectMapTreeBuilder();
        builder.add(tree,elements);
        print(tree,root.getId());
    }
}
