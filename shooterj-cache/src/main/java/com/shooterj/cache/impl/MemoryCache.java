package com.shooterj.cache.impl;

import com.shooterj.cache.ICache;
import com.shooterj.core.util.MapUtil;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 内存的cache实现
 * @author shooterJ
 */
@Service
public class MemoryCache<T extends Serializable> implements ICache<T> {
	private  HashMap<String,T> map=new HashMap<String, T>();

	/**
	 * 添加缓存
	 * <pre>
	 * 该实现使用HashMap存放缓存数据，无法实现缓存数据的自动过期.
	 * </pre>
	 */
	public boolean add(String key, T obj, int timeout) {
		map.put(key, obj);
		return true;
	}

	
	public boolean add(String key, T obj) {
		map.put(key, obj);
		return true;
	}

	
	public boolean delByKey(String key) {
		map.remove(key);
		return true;
	}

	
	public void clearAll() {
		map.clear();
	}

	
	public T getByKey(String key) {
		return map.get(key);
	}

	
	public boolean containKey(String key) {
		return map.containsKey(key);
	}


	@Override
	public void delByStartKey(String key) {
		MapUtil.delByStartKey(map, key);
	}

}
