package com.orderway.system.bus.modular.scope.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Dell
 */
@Data
@TableName("sys_data_scope")
public class SysDataScope implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 源id
     */
    private String sId;

    /**
     * 目标id
     */
    private String tId;

    /**
     * 源类型(USER:用户，ROLE:角色)
     */
    private String sType;

}
