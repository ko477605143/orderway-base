package com.orderway.system.bus.modular.scope.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.orderway.core.pojo.base.param.BaseGroups;
import com.orderway.core.pojo.base.param.BaseParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 数据授权表
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDataScopeParam extends BaseGroups {

    /**
     * 源id
     */
    @NotBlank(message = "源id不能为空", groups = {add.class, list.class})
    private String sId;

    /**
     * 授权数据
     */
    private String tId;

    /**
     * 源类型(USER:用户，ROLE:角色)
     */
    @NotBlank(message = "源类型(USER:用户，ROLE:角色)不能为空", groups = {add.class, list.class})
    private String sType;


}
