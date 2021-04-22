package com.shooterj.core.messagetrunk;

import com.shooterj.core.jedis.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import javax.annotation.PostConstruct;

/**
 * 基于redis轻量级消息总线框架。
 *
 * 开发宗旨：项目内的轻量级消息队列。 框架开发目的：在项目内部，我们常常需要做异步操作，常规的做法是提交给线程池去做，这样会导致一些：
 *
 * 线程池大小不可控，任务可能因为线程池满了被抛弃。
 * 任务执行失败没有重试机制。
 * 任务执行失败没有统一的异常处理。
 * 为了解决如上问题，基于redis的队列开发了该消息队列，具有如下特点：
 *
 * 足够轻量级，队列配置简单，只要使用redis即可，不需要额外部署环境；
 * 支持分布式，任务提交后由多台机器分布式处理，机器资源分配合理；
 * 处理效率高，任务交给多线程并发处理；
 * 处理有重试机制，并且可自定义错误处理。
 * 对于小型数据入队列，出队列效率高。
 */
public abstract class AbstarctMessageHandler<T> implements Runnable{

    private static Log logger = LogFactory.getLog(AbstarctMessageHandler.class);

    @Autowired
    protected MessageTrunk messageTrunk;


    /**
     * 初始化启动本监听器
     */
    @PostConstruct
    public void startListen()
    {
        // 启动监听
        new Thread(this).start();
    }

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 监听的消息类型
     */
    private String messageType;

    /**
     * 消息的class对象
     */
    private Class<T> clazz;

    private boolean monitor;

    // 默认为3
    private int retryTimes = 3;

    public AbstarctMessageHandler(String messageType, Class<T> clazz, int retryTimes)
    {
        this.messageType = messageType;
        this.clazz = clazz;
        this.retryTimes = 3;
    }

    public AbstarctMessageHandler(String messageType, Class<T> clazz)
    {
        this.messageType = messageType;
        this.clazz = clazz;
    }

    public void run()
    {
        while (true)
        {
            listen();
        }
    }

    /**
     * 监听消息
     */
    @SuppressWarnings("unchecked")
    public void listen()
    {
        // 阻塞获取redis列表值
        final Object obj = redisUtil.blpop(messageType, Integer.MAX_VALUE, Message.class);

        // 如果获取失败,以为着redis连接有问题
        if (obj == null)
        {
            monitor = false;
            logger.warn("消息分发器获取redis连接失败");
            // 暂停5秒钟,防止线程空跑,打印无数日志
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                logger.warn("消息分发器线程暂停失败");
            }

            return;
        }

        if (!monitor)
        {
            logger.warn("消息分发开始");
            monitor = true;
        }

        try
        {
            messageTrunk.getExecutorService().submit(new Runnable()
            {
                @Override
                public void run()
                {
                    Message message = (Message) obj;
                    try
                    {
                        // 如果获取成功，则交给子类做业务处理
                        handle((T) message.getContent());
                    }
                    catch (Exception ex)
                    {
                        logger.error(ex);
                        // 处理失败，判断是否需要重试
                        if (message.getFailTimes().intValue() >= retryTimes)
                        {
                            handleFailed((T) message.getContent());
                        }
                        else
                        {
                            message.setFailTimes(message.getFailTimes().intValue() + 1);
                            // 再次put回消息总线，等待下次重试
                            messageTrunk.put(message);

                            if (logger.isDebugEnabled())
                            {
                                StringBuilder sb = new StringBuilder();
                                sb.append("msg:[").append(message).append("], 执行失败，准备重试。");
                                logger.debug(sb.toString());
                            }
                        }
                    }
                }

            });

        }
        catch (TaskRejectedException ex)
        {
            logger.warn("线程池已满，准备回写任务，暂停本线程");
            // 如果发生任务拒绝加入线程池，则回写任务到redis，等待重试
            messageTrunk.put((Message) obj);

            // 暂停指定时间
            try
            {
                Thread.sleep(messageTrunk.getThreadPoolFullSleepSeconds() * 1000);
            }
            catch (InterruptedException e)
            {
                logger.warn("生产者暂停异常", ex);
            }
        }
        catch (Exception ex)
        {
            logger.error("消息总线发生异常", ex);
        }
    }

    /**
     * 获取到消息后做业务处理
     */
    public abstract void handle(T obj);

    /**
     * 消息多次重试处理失败
     */
    public abstract void handleFailed(T obj);

}
