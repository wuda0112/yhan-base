package com.wuda.yhan.util.commons.tree;

/**
 * 抽取公共内容.
 *
 * @author wuda
 */
abstract class AbstractNumberIdMapTreeBuilder {

    String getMessage(Number id, Number pid) {
        return "id=" + id + ",pid=" + pid + ",不能建立父子关系."
                + "因为父元素并不在给定的元素列表和树中."
                + "一种可能的原因是:此父元素根本不存在,这可以检查你的数据即可验证."
                + "最有可能的原因是:该父元素本身是存在的,但是在此之前并没有被添加到树中."
                + "这种情况的解决方案是:对于所有元素,必须先将父级先添加到树中,然后再添加子元素";
    }
}
