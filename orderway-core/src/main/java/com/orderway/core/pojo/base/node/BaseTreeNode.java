/*
create by   orderway   add time 20220909
 */
package com.orderway.core.pojo.base.node;

import java.util.List;

/**
 * 树节点接口
 *
 * @author oderway
 * @date 2020/4/5 14:07
 */
public interface BaseTreeNode {


    /**
     * 获取节点id
     *
     * @return 节点id
     * @author oderway
     * @date 2020/7/9 18:36
     */
    Long getId();

    /**
     * 获取节点父id
     *
     * @return 节点父id
     * @author oderway
     * @date 2020/7/9 18:36
     */
    Long getPid();

    /**
     * 设置children
     *
     * @param children 子节点集合
     * @author oderway
     * @date 2020/7/9 18:36
     */
    void setChildren(List children);
}
