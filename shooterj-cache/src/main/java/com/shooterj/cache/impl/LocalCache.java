package com.shooterj.cache.impl;

import com.shooterj.cache.ICache;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 本地内存缓存实现
 * @author shooterJ
 */
@Service
public class LocalCache<T extends Serializable> implements ICache {

	private static ConcurrentMap<String, LocalCacheData> cacheRepository = new ConcurrentHashMap<String, LocalCacheData>();   // 类型建议用抽象父类，兼容性更好；
	private static class LocalCacheData{
		private String key;
		private Object val;
		private long timeoutTime;

		public LocalCacheData() {
		}

		public LocalCacheData(String key, Object val, long timeoutTime) {
			this.key = key;
			this.val = val;
			this.timeoutTime = timeoutTime;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getVal() {
			return val;
		}

		public void setVal(Object val) {
			this.val = val;
		}

		public long getTimeoutTime() {
			return timeoutTime;
		}

		public void setTimeoutTime(long timeoutTime) {
			this.timeoutTime = timeoutTime;
		}
	}


	/**
	 * set cache
	 *
	 * @param key
	 * @param val
	 * @param cacheTime
	 * @return
	 */
	public boolean set(String key, Object val, long cacheTime){

		// clean timeout cache, before set new cache (avoid cache too much)
		cleanTimeoutCache();

		// set new cache
		if (key==null || key.trim().length()==0) {
			return false;
		}
		if (val == null) {
			del(key);
		}
		if (cacheTime <= 0) {
			del(key);
		}
		long timeoutTime = System.currentTimeMillis() + cacheTime;
		LocalCacheData localCacheData = new LocalCacheData(key, val, timeoutTime);
		cacheRepository.put(localCacheData.getKey(), localCacheData);
		return true;
	}

	/**
	 * remove cache
	 *
	 * @param key
	 * @return
	 */
	public  boolean del(String...key){
		if (key==null || key.length==0) {
			return false;
		}
		cacheRepository.remove(key);
		return true;
	}

	/**
	 * get cache
	 *
	 * @param key
	 * @return
	 */
	public  Object get(String key){
		if (key==null || key.trim().length()==0) {
			return null;
		}
		LocalCacheData localCacheData = cacheRepository.get(key);
		if (localCacheData!=null && System.currentTimeMillis()<localCacheData.getTimeoutTime()) {
			return localCacheData.getVal();
		} else {
			del(key);
			return null;
		}
	}

	/**
	 * clean timeout cache
	 *
	 * @return
	 */
	public boolean cleanTimeoutCache(){
		if (!cacheRepository.keySet().isEmpty()) {
			for (String key: cacheRepository.keySet()) {
				LocalCacheData localCacheData = cacheRepository.get(key);
				if (localCacheData!=null && System.currentTimeMillis()>=localCacheData.getTimeoutTime()) {
					cacheRepository.remove(key);
				}
			}
		}
		return true;
	}

}
