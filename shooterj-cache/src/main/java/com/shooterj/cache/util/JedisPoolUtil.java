package com.shooterj.cache.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisPoolUtil implements InitializingBean {
    /**
     * 日志
     */
    private static final Log logger = LogFactory.getLog(JedisPoolUtil.class);

    /**
     * REDIS连接池
     */
    private static volatile JedisPool pool;

    /**
     * 最大连接数，如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个JEDIS实例，则此时pool的状态为exhausted(耗尽
     * )。
     */
    @Value("${jedis.maxTotal}")
    private Integer maxTotal = 10000;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的JEDIS实例
     */
    @Value("${jedis.maxIdle}")
    private Integer maxIdle = 5;
    @Value("${jedis.minIdle}")
    private Integer minIdle = 1;

    /**
     * 最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
     */
    @Value("${jedis.maxWaitTime}")
    private Integer holed = 2000;

    /**
     * redis服务端口
     */
    @Value("${jedis.port}")
    private Integer port;

    /**
     * redis服务地址
     */
    @Value("${jedis.url}")
    private String host;

    /**
     * redis连接超时时间
     */
    @Value("${jedis.connectTimeout}")
    private Integer timeout = 30;

    /**
     * redis连接密码
     */
//	@Value("${jedis.password}")
    private String password;
    @Autowired
    Environment environment;

    /**
     *
     */
    private Integer DB = 2;

    public void setHoled(Integer holed) {
        this.holed = holed;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDB(Integer dB) {
        DB = dB;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * 初始化redisUtil实例，配置连接池
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(holed);
        config.setTestOnBorrow(false);
        String password = environment.getProperty("jedis.password");
        password = StringUtils.isNotBlank(password) ? password : null;
        pool = new JedisPool(config, host, port, timeout, password, DB);
    }

    /**
     * 从连接池获得一个redis连接
     *
     * @return
     */
    public static Jedis getConnent() {
        Jedis jedis = pool.getResource();
        return jedis;
    }

    /**
     * 关闭当前连接实例，将连接返回连接池
     *
     * @param jedis redis连接实例
     */
    public static void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error("close jedis failed!", e);
        }
    }


}
