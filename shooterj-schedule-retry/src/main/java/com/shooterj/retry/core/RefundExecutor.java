package com.shooterj.retry.core;

import com.shooterj.retry.scheduler.ExecutorBase;
import com.shooterj.retry.scheduler.ExecutorJobScheduler;
import com.shooterj.retry.scheduler.RetryExecutorBase;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

/**
 * @description: 退款任务
 */
public class RefundExecutor extends RetryExecutorBase {


  @Autowired
  private ExecutorJobScheduler scheduler;

  @Override
  public void run(Map<String, Object> params) {
    //Long orderId = (Long) params.get("orderId");
    //Payment payment = new Payment(orderId);
    //payment.refund();
    System.out.println("执行退款");
    //scheduler.scheduleRetry(RefundExecutor.class, params, 1000, 10000);

    }
}
