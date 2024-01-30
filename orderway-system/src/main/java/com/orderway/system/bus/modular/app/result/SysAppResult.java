package com.orderway.system.bus.modular.app.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.orderway.system.bus.modular.dict.entity.SysDictData;
import lombok.Data;

@Data
public class SysAppResult {
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
     * 字典表对应信息
     */
    private SysDictData sysDictData;
}
