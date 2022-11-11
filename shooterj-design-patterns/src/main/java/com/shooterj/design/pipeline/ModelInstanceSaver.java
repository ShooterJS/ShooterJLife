package com.shooterj.design.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 保存模型实例到相关DB表处理器
 */
@Slf4j
@Component
public class ModelInstanceSaver implements ContextHandler<InstanceBuildContext>{
    @Override
    public boolean handle(InstanceBuildContext context) {
        log.info("--保存模型实例到相关DB表--");

        // 假装保存模型实例

        return true;

    }
}
