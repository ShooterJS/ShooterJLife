package com.winway.demo.miaosha1;


import org.springframework.stereotype.Component;

/**
 * 主题分为秒杀和下单减库存两个主流程
 *
 * 准备工作，定时器将数据库中的商品库存预热到redis
 * 根据goodsid获取随机名称(这样确保前端用户不能提前知道秒杀链接，降低被刷风险)
 *
 * 1.用户恶意请求过滤 ：没有绝对的公平
 *    规则：10秒10个请求加入黑名单
 *    1.1：lrange取最近十条访问记录，十条最早的一条和当前时间比较，
 *    1.2：超过十秒则加入黑名单使用redisHash实现，清除访问记录
 *    1.3：否则用户请求（mobile,requestTimeStamp）lpush到redisList
 *
 * 2. 限流
 *    2.1:数据库中配置限制流量，取出当前商品是否需要多少流量进入 limit,比较链表中的条数  redisList -> llen，超过就直接打回
 *
 * 3. 若是在请求列表就打回，不允许重复请求，秒杀请求过的添加到处理列表，redisHash，
 *
 * 4. 秒杀请求加入到消息队列，rpush
 *
 * 5. 线程异步循环处理，利用Bpop命令取出消息队列中的消息，进行处理
 *
 * 6.消息处理：
 *      6.1：检测为黑名单用户，拒绝抢购
 *      6.2：抱歉，您来晚了，抢购已经结束了
 *      6.3：减redis库存 decr
 *      6.4：减库存成功后，生成下单成功的token，并存入redisString，生成有效期2分钟，供前端获取
 *      6.5：如果减库存失败，则消息记录回到消息队列中，等待再次处理
 *
 * 7.用户下单
 *      7.1：前端轮训token接口，得到token
 *      7.2: 调用下单接口，校验。判断token是否存在或者是否失效，将处理列表redishash清除，验完清除key，失效了则返还库存
 *      7.3: 校验库存是否剩余，不剩将redis的库存状态设置为已结束
 *      7.4：库存剩余，则将更新数据库，生成订单
 */
@Component
public class MiaoshaService {


}
