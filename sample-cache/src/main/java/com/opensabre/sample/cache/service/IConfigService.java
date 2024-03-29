package com.opensabre.sample.cache.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import static io.github.opensabre.cache.redis.DefaultCacheArea.SHORT_TIME_AREA;

public interface IConfigService {

    String getConfig(String key);

    //获取并缓存，使用shortTime(local缓存1分钟，remote缓存5分钟)进行user缓存，userId为key，缓存名称为 userCache:${userId}
    @Cached(name = "userCache:", area = SHORT_TIME_AREA, key = "#userId", cacheType = CacheType.BOTH)
    String getUser(String userId);
    //更新缓存，使用当前值更新缓存名称为 userCache:${userId}
    @CacheUpdate(name = "userCache:", area = SHORT_TIME_AREA, key = "#userId", value = "#user")
    void updateUser(String userId, String user);
    //清除缓存名称为 userCache:${userId} 的缓存
    @CacheInvalidate(name = "userCache:", area = SHORT_TIME_AREA, key = "#userId")
    void deleteUser(String userId);
}
