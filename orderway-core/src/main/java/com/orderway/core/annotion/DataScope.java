/*
create by   orderway   add time 20220909
 */
package com.orderway.core.annotion;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author oderway
 * @date 2020/3/28 17:16
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScope {
}
