package com.shooterj.cache;

/**
 * 缓存操作接口
 * <pre>
 * 定义了增加缓存，删除缓存，清除缓存，读取缓存的接口
 * </pre>
 *
 * @author shooterJ
 */
public interface ICache {

    public boolean set(String key, Object value, long time);

    public  boolean del(String...key);

    public  Object get(String key);
}