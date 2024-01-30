/*
create by orderway   add time  20220909
 */
package com.orderway.base.pojo.dict;

import com.orderway.base.annotation.ChineseDescription;
import lombok.Data;

/**
 * 只包含id,name,code三个字段的pojo
 * <p>
 * 一般用在获取某个业务的下拉列表的返回bean
 * <p>
 * 例如，返回用户下拉列表，只需返回用户id和姓名即可
 * <p>
 * 例如，返回角色下拉列表，只需返回角色id和角色名称
 *
 * @author fengshuonan
 * @date 2020/11/21 16:53
 */
@Data
public class SimpleDict {

    /**
     * id
     */
    @ChineseDescription("id")
    private Long id;

    /**
     * 名称
     */
    @ChineseDescription("名称")
    private String name;

    /**
     * 编码
     */
    @ChineseDescription("编码")
    private String code;

}
