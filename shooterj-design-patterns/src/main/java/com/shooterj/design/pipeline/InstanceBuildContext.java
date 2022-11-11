package com.shooterj.design.pipeline;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 为了方便说明，我们现在先定义出最早版 【提交模型逻辑】 的上下文和相关处理器：
 * 模型实例构建的上下文
 */
@Getter
@Setter
public class InstanceBuildContext extends PipelineContext{

    /**
     * 模型 id
     */
    private Long modelId;

    /**
     * 用户 id
     */
    private long userId;

    /**
     * 表单输入
     */
    private Map<String, Object> formInput;

    /**
     * 保存模型实例完成后，记录下 id
     */
    private Long instanceId;

    /**
     * 模型创建出错时的错误信息
     */
    private String errorMsg;

    // 其他参数

    @Override
    public String getName() {
        return "模型实例构建上下文";
    }

}
