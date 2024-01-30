/*
create by orderway   add time  20220909
 */
package com.orderway.cache.sdk.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.orderway.cache.api.CacheOperatorApi;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 基于redis的缓存封装
 *
 * @author oderway
 * @date 2020/7/9 10:09
 */
public abstract class AbstractRedisCacheOperator<T> implements CacheOperatorApi<T> {

    private final RedisTemplate<String, T> redisTemplate;

    public AbstractRedisCacheOperator(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, T value) {
        redisTemplate.boundValueOps(calcKey(key)).set(value);
    }

    @Override
    public void put(String key, T value, Long timeoutSeconds) {
        redisTemplate.boundValueOps(calcKey(key)).set(value, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public T get(String key) {
        return redisTemplate.boundValueOps(calcKey(key)).get();
    }

    @Override
    public void remove(String... key) {
        ArrayList<String> keys = CollectionUtil.toList(key);
        List<String> withPrefixKeys = keys.stream().map(this::calcKey).collect(Collectors.toList());
        redisTemplate.delete(withPrefixKeys);
    }

    @Override
    public void expire(String key, Long expiredSeconds) {
        redisTemplate.boundValueOps(calcKey(key)).expire(expiredSeconds, TimeUnit.SECONDS);
    }

    @Override
    public boolean contains(String key) {
        T value = redisTemplate.boundValueOps(calcKey(key)).get();
        return value != null;
    }

    @Override
    public Collection<String> getAllKeys() {
        Set<String> keys = redisTemplate.keys(getCommonKeyPrefix() + "*");
        if (keys != null) {
            // 去掉缓存key的common prefix前缀
            return keys.stream().map(key -> StrUtil.removePrefix(key, getCommonKeyPrefix())).collect(Collectors.toSet());
        } else {
            return CollectionUtil.newHashSet();
        }
    }

    @Override
    public Collection<T> getAllValues() {
        Set<String> keys = redisTemplate.keys(getCommonKeyPrefix() + "*");
        if (keys != null) {
            return redisTemplate.opsForValue().multiGet(keys);
        } else {
            return CollectionUtil.newArrayList();
        }
    }

    @Override
    public Map<String, T> getAllKeyValues() {
        Collection<String> allKeys = this.getAllKeys();
        HashMap<String, T> results = MapUtil.newHashMap();
        for (String key : allKeys) {
            results.put(key, this.get(key));
        }
        return results;
    }

    /**
     * 获取RedisTemplate
     *
     * @author fengshuonan
     * @date 2021/2/8 9:40
     */
    public RedisTemplate<String, T> getRedisTemplate() {
        return this.redisTemplate;
    }

}
