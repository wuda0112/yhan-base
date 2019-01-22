package com.wuda.yhan.util.commons.unique;

import java.io.Serializable;

/**
 * 用ID/PID模型表示父子关系的数据.
 * 比如经常看到数据库中的产品分类,文章分类等数据都是ID/PID模型.
 *
 * @author wuda
 * @see IntIdPidObject
 * @see LongIdPidObject
 */
public interface NumberIdPidObject extends Serializable, NumberIdObject {

}
