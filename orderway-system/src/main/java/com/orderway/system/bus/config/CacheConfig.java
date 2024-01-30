/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import com.orderway.core.context.constant.ConstantContextHolder;
import com.orderway.core.pojo.login.SysLoginUser;
import com.orderway.system.bus.core.cache.AppCache;
import com.orderway.system.bus.core.cache.MappingCache;
import com.orderway.system.bus.core.cache.ResourceCache;
import com.orderway.system.bus.core.cache.UserCache;
import com.orderway.system.bus.core.redis.FastJson2JsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;

/**
 * 缓存的配置，默认使用基于内存的缓存，如果分布式部署请更换为redis
 *
 * @author oderway
 * @date 2020/7/9 11:43
 */
@Configuration
public class CacheConfig {

    /**
     * url资源的缓存，默认不过期
     *
     * @author oderway
     * @date 2020/7/9 11:44
     */
    @Bean
    public ResourceCache resourceCache() {
        return new ResourceCache();
    }

    /**
     * 登录用户的缓存，默认过期时间，根据系统sys_config中的常量决定
     *
     * @author oderway
     * @date 2020/7/9 11:44
     */
//    @Bean
//    public UserCache userCache() {
//        TimedCache<String, SysLoginUser> timedCache =
//                CacheUtil.newTimedCache(ConstantContextHolder.getSessionTokenExpireSec() * 1000);
//
//        //定时清理缓存，间隔1秒
//        timedCache.schedulePrune(1000);
//
//        return new UserCache(timedCache);
//    }

    @Bean
    public RedisTemplate<String, SysLoginUser> userCacheRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, SysLoginUser> userRedisTemplate = new RedisTemplate<>();
        userRedisTemplate.setConnectionFactory(factory);
        userRedisTemplate.setKeySerializer(new StringRedisSerializer());
        userRedisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer<>(SysLoginUser.class));
        userRedisTemplate.afterPropertiesSet();
        return userRedisTemplate;
    }

    @Bean
    public UserCache userCache(RedisTemplate<String, SysLoginUser> userCacheRedisTemplate) {
        return new UserCache(userCacheRedisTemplate);
    }

    @Bean
    public AppCache appCache(RedisTemplate<String, String> userCacheRedisTemplate) {
        return new AppCache(userCacheRedisTemplate);
    }

    /**
     * mapping映射缓存
     *
     * @author fengshuonan
     * @date 2020/7/24 13:55
     */
    @Bean
    public MappingCache mappingCache() {
        TimedCache<String, Map<String, Object>> timedCache =
                CacheUtil.newTimedCache(2 * 60 * 1000);

        // 定时清理缓存，间隔1秒
        timedCache.schedulePrune(1000);

        return new MappingCache(timedCache);
    }

}
