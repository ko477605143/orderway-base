package com.orderway.system.bus.modular.account.param;

import com.orderway.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 账户表参数信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAccountParam extends BaseParam {

    /**
     * 主键
     */
    @NotNull(message = "账户id不能为空,请检查id参数",groups = {edit.class,updatePwd.class,resetPwd.class,delete.class})
    private Long id;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空,请检查code属性",
            groups = {add.class,edit.class})
    private String code;

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空,请检查account属性",
            groups = {add.class,edit.class})
    @Length(message = "名称不能超过{max}个字符",max = 30)
    private String account;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空,请检查password属性",
            groups = {add.class,updatePwd.class})
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空,请检查password属性",
            groups = {updatePwd.class})
    private String newPassword;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数",
            groups = {add.class,edit.class})
    private Long sort;

    /**
     * 状态（字典 0正常 1停用 2删除）
     */
    @NotNull(message = "状态不能为空，请检查status参数",
    groups = {changeStatus.class})
    private Long status;

    /**
     * 备注1
     */
    private String remark1;

    /**
     * 备注2
     */
    private String remark2;

    /**
     * 用户Id
     */
    @NotNull(message = "用户Id不能为空，请检查userId参数",
            groups = {add.class,detail.class,edit.class})
    private Long userId;
}
