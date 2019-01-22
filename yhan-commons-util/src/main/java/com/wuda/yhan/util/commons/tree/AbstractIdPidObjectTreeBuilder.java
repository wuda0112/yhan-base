package com.wuda.yhan.util.commons.tree;

import com.wuda.yhan.util.commons.unique.NumberIdPidObject;

import java.util.List;

/**
 * {@inheritDoc}
 *
 * @author wuda
 */
public abstract class AbstractIdPidObjectTreeBuilder<T extends NumberIdPidObject> implements IdPidObjectTreeBuilder {

    /**
     * {@inheritDoc}
     *
     * @deprecated 使用对应的具体数据类型的替代
     */
    @Override
    public void add(RelationshipTree tree, List elements) {
        throw new RuntimeException();
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated 使用对应的具体数据类型的替代
     */
    @Override
    public void add(RelationshipTree tree, NumberIdPidObject element) {
        throw new RuntimeException();
    }

}
