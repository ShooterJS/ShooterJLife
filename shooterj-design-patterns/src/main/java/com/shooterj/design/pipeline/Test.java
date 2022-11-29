package com.shooterj.design.pipeline;

import com.shooterj.design.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * pipeline 模式
 * 行为：处理请求
 * 属性：通用请求参数，用户id,模型id,数据id等
 *
 * 定义管理通道上下文
 * 定义上下文处理器
 * 模型实例构建的上下文
 * 构建管道执行器
 * 使用管道模式
 *
 * 1.埋点中台DDD领域驱动设计：页面停留，行为漏斗分析，活跃分析。
 * 2.用户组织架构信息曝光组件： 本地LRUCache + Redis + ThreadLocal线程副本 + WebMvc-Intercepter
 * 3.复杂流程代码通用组件：准备路由表，指定好Context和管道的映射，以及管道中处理器的顺序
 *      key为：Context类型，value为：执行器列表，Spring根据这份路由表，在启动时构建一个Map，业务方调用流程启动器开启流程
 */
@Slf4j
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class Test {

    /**
     * 版本1：
     */
    /*public CommonReponse<Long> buildModelInstance(InstanceBuildRequest request) {
        // 输入数据校验
        validateInput(request);
        // 根据输入创建模型实例
        ModelInstance instance = createModelInstance(request);
        // 保存实例到相关 DB 表
        saveInstance(instance);
    }*/

    @Autowired
    PipelineExecutor pipelineExecutor;

    /**
     * 版本2
     */
   @org.junit.Test
    public void buildModelInstance() {
        InstanceBuildContext data = new InstanceBuildContext();
        boolean success = pipelineExecutor.acceptSync(data);
    }

}
