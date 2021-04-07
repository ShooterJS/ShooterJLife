package com.winway.demo.repeat.job;

import com.winway.demo.jedis.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多节点定时器不允许重复执行（没必要的开销，逻辑的错乱，多线程安全，数据污染，数据库死锁等问题）。
 * 1、拿到当前执行的 appid+类+方法 = key。
 * 2、查询key是否存在，是则打回。
 * 3、否则将key 存入redis，设置过期时间，精确到毫秒，执行定时器业务逻辑。
 * 4、执行完后则需要将主动释放锁。
 * 注意：过期时间默认为 60s 的生命周期
 * 若是一天执行一次，可以不用加过期时间参数
 * <p>
 * 痛点：就是在过期时间内没有执行完，但下个周期的定时任务又要过来，会带来多线程安全问题
 * <p>
 * 就比如定时器一秒钟执行一次，但是第一次定时器没有在一秒内执行完，到了下一秒定时器任务过来，会带来多线程安全问题
 * 比较low的做法：
 * 根据测试业务执行时间报告，延长过期时间（5s），顶多就是浪费了几次定时器任务而已
 * <p>
 * 比较高级的做法：
 * TODO 自动延续未执行完的任务的key的生命周期
 */

@Slf4j
@Aspect
@Component
public class RepeatJobAop {

    @Autowired
    RedisUtil redisUtil;

    @Pointcut("@annotation(com.winway.demo.repeat.job.RepeatJob)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public void beforeService(ProceedingJoinPoint jointPoint) throws Throwable {
        KeyInfo keyInfo = getKeyInfo(jointPoint);
        String key = keyInfo.getKey();
        if (key != null) {
            try {
                boolean success = redisUtil.setnx(key, "", keyInfo.getExpire());
                if (success) {
                    //加key成功放行
                    jointPoint.proceed();
                    System.out.println("加key成功放行");
                } else {
                    //没有抢到锁则打回，不让重复执行
                    return;
                }
            } catch (Exception e) {
                log.error("加锁的程序执行出错了",e);
            } finally {
                //执行完后删除key，等待下一次定时器任务的执行
                redisUtil.delete(keyInfo != null ? key : "");

            }


        } else {
            jointPoint.proceed();
        }

    }


    public KeyInfo getKeyInfo(ProceedingJoinPoint jointPoint) throws Exception {
        //获得当前访问的class
        Class<?> clazz = jointPoint.getTarget().getClass();
        //获得访问的方法名
        String methodName = jointPoint.getSignature().getName();
        //得到方法信息
        Method method = clazz.getDeclaredMethod(methodName);
        RepeatJob repeatJob = method.getAnnotation(RepeatJob.class);
        // 判断是否存在@RepeatJob注解
        if (repeatJob != null) {
            RepeatJob annotation = repeatJob;
            int expire = annotation.expire();
            String uniqueAppName = annotation.uniqueAppName();
            String key = new StringBuilder().append(uniqueAppName).append(clazz.getSimpleName()).append(methodName).toString();
            return KeyInfo.builder().key(key).expire(expire).build();
        } else {
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class KeyInfo {
        String key;
        int expire;
    }

}
