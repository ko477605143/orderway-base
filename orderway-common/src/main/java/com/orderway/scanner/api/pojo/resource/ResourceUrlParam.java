/*
create by orderway   add time  20220909
 */
package com.orderway.scanner.api.pojo.resource;

import com.orderway.rule.pojo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取资源通过url请求
 *
 * @author fengshuonan
 * @date 2020/10/19 22:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceUrlParam extends BaseRequest {

    private String url;

}
