/*
create by orderway   add time  20220909
 */
package com.orderway.cache.sdk.memory;

import cn.hutool.cache.impl.TimedCache;
import com.orderway.cache.api.constants.CacheConstants;

/**
 * 默认内存缓存的实现，value存放String类型
 *
 * @author fengshuonan
 * @date 2021/2/24 22:16
 */
public class DefaultStringMemoryCacheOperator extends AbstractMemoryCacheOperator<String> {

    public DefaultStringMemoryCacheOperator(TimedCache<String, String> timedCache) {
        super(timedCache);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
