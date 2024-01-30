/*
create by orderway   add time  20220909
 */
package com.orderway.cache.sdk.redis.operator;

import com.orderway.cache.api.constants.CacheConstants;
import com.orderway.cache.sdk.redis.AbstractRedisCacheOperator;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认redis缓存的实现，value存放String类型
 *
 * @author fengshuonan
 * @date 2021/2/24 22:16
 */
public class DefaultStringRedisCacheOperator extends AbstractRedisCacheOperator<String> {

    public DefaultStringRedisCacheOperator(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return CacheConstants.DEFAULT_STRING_CACHE_PREFIX;
    }

}
