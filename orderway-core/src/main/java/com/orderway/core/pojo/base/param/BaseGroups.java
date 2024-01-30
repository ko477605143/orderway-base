package com.orderway.core.pojo.base.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseGroups implements Serializable {

    /**
     * 参数校验分组：分页
     */
    public @interface page {
    }

    /**
     * 参数校验分组：列表
     */
    public @interface list {
    }

    /**
     * 参数校验分组：下拉
     */
    public @interface dropDown {
    }

    /**
     * 参数校验分组：增加
     */
    public @interface add {
    }

    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }

    /**
     * 参数校验分组：更新信息
     */
    public @interface updateInfo {
    }

    /**
     * 参数校验分组：修改密码
     */
    public @interface updatePwd {
    }

    /**
     * 参数校验分组：重置密码
     */
    public @interface resetPwd {
    }

    /**
     * 参数校验分组：修改头像
     */
    public @interface updateAvatar {
    }

    /**
     * 参数校验分组：删除
     */
    public @interface delete {
    }

    /**
     * 参数校验分组：详情
     */
    public @interface detail {
    }

    /**
     * 参数校验分组：授权角色
     */
    public @interface grantRole {
    }

    /**
     * 参数校验分组：授权菜单
     */
    public @interface grantMenu {
    }

    /**
     * 参数校验分组：授权数据
     */
    public @interface grantData {
    }

    /**
     * 参数校验分组：强退
     */
    public @interface force {
    }

    /**
     * 参数校验分组：停用
     */
    public @interface stop {
    }

    /**
     * 参数校验分组：启用
     */
    public @interface start {
    }

    /**
     * 参数校验分组：部署
     */
    public @interface deploy {
    }

    /**
     * 参数校验分组：挂起
     */
    public @interface suspend {
    }

    /**
     * 参数校验分组：激活
     */
    public @interface active {
    }

    /**
     * 参数校验分组：调试
     */
    public @interface debug {
    }

    /**
     * 参数校验分组：委托
     */
    public @interface entrust {
    }

    /**
     * 参数校验分组：转办
     */
    public @interface turn {
    }

    /**
     * 参数校验分组：追踪
     */
    public @interface trace {
    }

    /**
     * 参数校验分组：跳转
     */
    public @interface jump {
    }

    /**
     * 参数校验分组：同意
     */
    public @interface agree {
    }

    /**
     * 参数校验分组：退回
     */
    public @interface back {
    }

    /**
     * 参数校验分组：终止
     */
    public @interface end {
    }

    /**
     * 参数校验分组：导出
     */
    public @interface export {
    }

    /**
     * 参数校验分组：映射
     */
    public @interface mapping {
    }

    /**
     * 参数校验分组：切换
     */
    public @interface change {
    }

    /**
     * 参数校验分组：历史审批记录
     */
    public @interface commentHistory {
    }

    /**
     * 参数校验分组：修改状态
     */
    public @interface changeStatus {
    }

    /**
     * 参数校验分组：传阅
     */
    public @interface circulate {
    }

    /**
     * 参数校验分组：加签
     */
    public @interface addSign {
    }

    /**
     * 参数校验分组：减签
     */
    public @interface deleteSign {
    }

    /**
     * 参数校验分组：添加节点
     */
    public @interface addNode {
    }
}
