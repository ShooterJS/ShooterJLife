package com.shooterj.cache.factory;

import com.shooterj.cache.ICache;
import com.shooterj.cache.impl.LocalCache;
import com.shooterj.cache.impl.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 作者:ShooterJ
 * 邮箱:prd-jiangshuai@winwayworld.com
 * 日期:2021/6/22
 */
@Component
public class CacheFactory {

    @Autowired
    RedisCache redisCache;
    @Autowired
    LocalCache localCache;


    public  ICache getCahce(Integer cacheType) {
        return cacheType == 1 ? localCache : redisCache;
    }
}
