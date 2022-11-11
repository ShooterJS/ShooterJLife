package com.shooterj.design.pipeline;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义上下文处理器
 */
public interface ContextHandler<T extends PipelineContext> {

    boolean handle(T context);
}
