/*
create by   orderway   add time 20220909
 */
package com.orderway.core.pojo.node;

import com.orderway.core.pojo.base.node.BaseTreeNode;
import lombok.Data;

import java.util.List;

/**
 * 通用树节点
 *
 * @author oderway
 * @date 2020/3/26 14:29
 */
@Data
public class CommonBaseTreeNode implements BaseTreeNode {

    /**
     * 节点id
     */
    private Long id;

    /**
     * 节点父id
     */
    private Long pid;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 子节点集合
     */
    private List children;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Long getPid() {
        return this.pid;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }

    /*@Override
    public String getNodeId() {
        return this.nodeId;
    }

    @Override
    public String getNodeParentId() {
        return this.nodeParentId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.childrenNodes = childrenNodes;
    }*/
}
