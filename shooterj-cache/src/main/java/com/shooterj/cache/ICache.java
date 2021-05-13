package com.shooterj.cache;

import java.io.Serializable;

/**
 * 缓存操作接口
 * <pre>
 * 定义了增加缓存，删除缓存，清除缓存，读取缓存的接口
 * </pre>
 * @author shooterJ
 */
public interface ICache<T extends Serializable> {
	/**
	 * 添加缓存
	 * @param key 缓存Key
	 * @param obj 缓存值
	 * @param timeout 保存时限(单位：秒)
	 */
	boolean add(String key,T obj,int timeout);
	
	/**
	 * 添加缓存
	 * @param key 缓存Key
	 * @param obj 缓存值
	 */
	boolean add(String key,T obj);
	
	/**
	 * 根据键删除缓存
	 * @param key 缓存Key
	 */
	boolean delByKey(String key);
	
	/**
	 * 根据键删除缓存
	 * @param key 缓存Key
	 */
	void delByStartKey(String key);
	
	/**
	 * 清除所有的缓存
	 */
	void clearAll();
	
	/**
	 * 读取缓存
	 * @param key 缓存Key
	 * @return 缓存值
	 */
	T getByKey(String key);
	
	/**
	 * 是否包含key
	 * @param key 缓存Key
	 * @return 是否包含该缓存Key
	 */
	boolean containKey(String key);
}