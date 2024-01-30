/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.modular.app.param;

import com.orderway.core.pojo.base.param.BaseParam;
import com.orderway.core.validation.flag.FlagValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

/**
 * 系统应用参数
 *
 * @author oderway
 * @date 2020/3/24 20:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysAppParam extends BaseParam {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long id;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空，请检查name参数", groups = {add.class, edit.class})
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空，请检查code参数", groups = {add.class, edit.class})
    private String code;

    /**
     * 是否默认激活（Y-是，N-否）,只能有一个系统默认激活
     * 用户登录后默认展示此系统菜单
     */
    @NotBlank(message = "是否默认激活不能为空，请检查active参数", groups = {add.class, edit.class})
    @FlagValue(message = "是否默认激活格式错误，正确格式应该Y或者N，请检查active参数", groups = {add.class, edit.class})
    private String active;

    /**
     * 客戶端类型
     */
    @NotNull(message = "数据范围类型不能为空，请检查dataScopeType参数", groups = {add.class,grantData.class})
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
    private String sort;

    /**
     *
     */
    private String appId;

    private String appKey;

    private Integer status;

    //@NotNull(message = "url类型不能为空", groups = {add.class, edit.class})
    //@Min(value = 1, message = "url类型错误，(1-全路径 2-相对路径)", groups = {add.class, edit.class})
    //@Max(value = 2, message = "url类型错误，(1-全路径 2-相对路径)", groups = {add.class, edit.class})
    private Integer urlType;
}
