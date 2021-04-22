package com.shooterj.core.messagetrunk;

import com.shooterj.core.jedis.RedisUtil;
import com.shooterj.core.threadpool.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * 消息总线，用于向消息总线存入消息
 *
 * @author dingxiangyong 2016年7月28日 下午1:42:42
 */
@Service
public class MessageTrunk
{
	@Autowired
	private RedisUtil redisUtil;

	private ExecutorService executorService = ThreadUtil.newExecutor(1, 4, 100);


	/**
	 * 失败重试次数，超过此值则不再重试，默认3次
	 */
	private int failRetryTimes = 3;

	/**
	 * 如果线程池满了，生产者暂停的时间，单位：S
	 */
	private int threadPoolFullSleepSeconds = 1;

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public int getFailRetryTimes()
	{
		return failRetryTimes;
	}

	public void setFailRetryTimes(int failRetryTimes)
	{
		this.failRetryTimes = failRetryTimes;
	}

	public int getThreadPoolFullSleepSeconds()
	{
		return threadPoolFullSleepSeconds;
	}

	public void setThreadPoolFullSleepSeconds(int threadPoolFullSleepSeconds)
	{
		this.threadPoolFullSleepSeconds = threadPoolFullSleepSeconds;
	}

	/**
	 * 推送消息
	 *
	 * @param message
	 */
	public void put(Message message)
	{
		redisUtil.rpush(message.getKey().toString(), message, 0);
	}
}
