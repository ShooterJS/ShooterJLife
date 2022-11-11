package com.shooterj.design.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 根据输入创建模型实例处理器
 */
@Slf4j
@Component
public class ModelInstanceCreator implements ContextHandler<InstanceBuildContext>{

    @Override
    public boolean handle(InstanceBuildContext context) {
        log.info("--根据输入数据创建模型实例--");

        // 假装创建模型实例

        return true;
    }
}
