package com.shooterj.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService<T extends Serializable> extends ICache<T> {

     Set<String> keys(String keyPattern);

     boolean hset(String key, String fieldName, Object object);

     <T> boolean hset(String key, String fieldName, T object, int seconds);

     <T> boolean hdel(String key, String fieldName);

     <T> T hget(String key, String fieldName, Class<T> clazz);

     boolean add(String key, T object);

     <T> T getByKey(String key, Class<T> clazz);

     Long getExpireSeconds(String key);

     long deleteRegEx(String key);

     <T> Long lpush(String key, T value, int second);

     void lpushStr(String key, String value, int second);

     String setMapData(String key, Map<String, String> mapData);

     Map<String, String> getMapData(String key);

     <T> List<T> lrange(String key, int start, int end, Class<T> clazz);

     <T> boolean lpush(String key, T object);

     boolean zincrby(String key, double score, Object object);

     boolean zadd(String key, double score, Object object);

     boolean zadd(String key, Map<String, Double> scoreMembers, int seconds);

     Long zcount(String key, double minScore, double maxScore);

     Long zrem(String key, String... members);

     Long zremrangeByLex(String key, String minValue, String maxValue);

     Set<String> zrangeAll(String key, int offset, int count);

     Set<String> zrangeAll(String key);

     Set<String> zrangeByScoreGreaterOrEqual(String key, String minScore);

     Set<String> zrangeByScoreGreaterOrEqual(String key, String minScore, int offset, int count);

     Set<String> zrangeByScoreLessOrEqual(String key, String maxScore);

     Set<String> zrangeByScoreLessOrEqual(String key, String maxScore, int offset, int count);

     Set<String> zrangeByScore(String key, String minScore, String maxScore);

     Set<String> zrangeByScore(String key, String minScore, String maxScore, int offset, int count);

     Set<String> zrangeByScore(String key, double minScore, double maxScore);

     Set<String> zrevrangeAll(String key, int offset, int count);

     Set<String> zrevrangeAll(String key);

     Set<String> zrevrangeByScoreGreaterOrEqual(String key, String minScore);

     Set<String> zrevrangeByScoreGreaterOrEqual(String key, String minScore, int offset, int count);

     Set<String> zrevrangeByScoreLessOrEqual(String key, String maxScore);

     Set<String> zrevrangeByScoreLessOrEqual(String key, String maxScore, int offset, int count);

     Set<String> zrevrangeByScore(String key, String maxScore, String minScore);

     Set<String> zrevrangeByScore(String key, String maxScore, String minScore, int offset, int count);

     Set<String> zrevrangeByScore(String key, double maxScore, double minScore);

     Long zremrangeByScore(String key, double maxScore, double minScore);

     boolean exists(String key);

     boolean getLock(String key);

     boolean releaseLock(String key);

     Long expire(String key, int seconds);

     boolean setnx(String key, Object object, int seconds);

     boolean setnx(String key, Object object);

     Long incr(String key);

     Long decr(String key);

     abstract <T2> T2 blpop(String key, int waitSeconds, Class<T2> clazz);

     <T2> Long rpush(String key, T2 value, int second);
}
