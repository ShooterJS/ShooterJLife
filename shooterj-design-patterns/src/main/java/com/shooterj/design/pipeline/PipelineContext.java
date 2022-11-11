package com.shooterj.design.pipeline;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 定义管理通道上下文
 */
@Getter
@Setter
public class PipelineContext {

    /**
     * 处理开始时间
     */
    public LocalDateTime startTime;

    /**
     * 处理结束时间
     */
    public LocalDateTime endTime;

    public String getName(){
        return this.getClass().getSimpleName();
    }

}
