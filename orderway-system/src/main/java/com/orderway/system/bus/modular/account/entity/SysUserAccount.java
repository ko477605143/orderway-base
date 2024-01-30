package com.orderway.system.bus.modular.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user_account")
public class SysUserAccount {

    /**
     * 主键Id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 账号表Id
     */
    private Long accountId;
}
