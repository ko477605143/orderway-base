/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.app.entity;

import com.orderway.core.pojo.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统应用表
 *
 * @author oderway
 * @date 2020/3/11 12:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_app")
public class SysApp extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 是否默认激活（Y-是，N-否）,只能有一个系统默认激活
     * 用户登录后默认展示此系统菜单
     */
    private String active;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    private Integer status;

    /**
     * 客戶端类型
     */
    private String clientType;

    /**
     * 路径
     */
    private String url;

    /**
     * 类型
     */
    private String pType;

    /**
     * 路径名称
     */
    private String pathName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     *
     */
    private String appId;


    private String appKey;

    private String appToken;

    private Integer urlType;
}
