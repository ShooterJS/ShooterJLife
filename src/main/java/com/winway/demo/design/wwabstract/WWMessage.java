package com.winway.demo.design.wwabstract;

import java.io.Serializable;

/**
 * 消息实体，消息有KEY 和content两部分组成， 注意：content必须实现序列化接口
 * 
 * @author dingxiangyong 2016年7月28日 下午1:40:32
 */
public class WWMessage implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8657613687306891080L;

	/**
	 * 消息唯一标识
	 */
	private String key;

	/**
	 * 消息具体内容
	 */
	private Object content;

	/**
	 * 执行失败次数
	 */
	private Integer failTimes;

	public WWMessage(String key, Object content)
	{
		super();
		this.key = key;
		this.content = content;
		this.failTimes = new Integer(0);
	}

	public WWMessage()
	{
		super();
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Object getContent()
	{
		return content;
	}

	public void setContent(Object content)
	{
		this.content = content;
	}

	public Integer getFailTimes()
	{
		return failTimes;
	}

	public void setFailTimes(Integer failTimes)
	{
		this.failTimes = failTimes;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Message [key=");
		builder.append(key);
		builder.append(", content=");
		builder.append(content);
		builder.append(", failTimes=");
		builder.append(failTimes);
		builder.append("]");
		return builder.toString();
	}

}
