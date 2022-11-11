package com.shooterj.design.pipeline;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 构建管道路由表
 * 到这里，有个问题就出现了：应该使用什么样的方式，将同一种 Context 的 ContextHandler 串联为管道呢？思考一下：
 * <p>
 * 1、给 ContextHandler 加一个 setNext 方法，每个实现类必须指定其下一个处理器。缺点也很明显，如果在当前管道中间加入一个新的 ContextHandler，那么要势必要修改前一个 ContextHandler 的 setNext 方法；另外，代码是写给人阅读的，这样做没法一眼就直观的知道整个管道的处理链路，还要进入到每个相关的 ContextHandler 中去查看才知道。
 * <p>
 * 2、给 ContextHandler 加上 @Order 注解，根据 @Order 中给定的数字来确定每个 ContextHandler 的序列，一开始时每个数字间隔的可以大些（比如 10、20、30），后续加入新的 ContextHandler 时，可以指定数字为 （11、21、31）这种，那么可以避免上面方案中要修改代码的问题，但是仍然无法避免要进入每个相关的 ContextHandler 中去查看才能知道管道处理链路的问题。
 * <p>
 * 3、提前写好一份路由表，指定好 ”Context -> 管道“ 的映射（管道用 List 来表示），以及管道中处理器的顺序 。
 * Spring 来根据这份路由表，在启动时就构建好一个 Map，Map 的键为 Context 的类型，值为 管道（即 List）。
 * 这样的话，如果想知道每个管道的处理链路，直接看这份路由表就行，
 * 一目了然。缺点嘛，就是每次加入新的 ContextHandler 时，这份路由表也需要在对应管道上进行小改动 —— 但是如果能让阅读代码更清晰，我觉得这样的修改是值得的、可接受的~
 */
@Configuration
public class PipelineRouteConfig implements ApplicationContextAware {
    private ApplicationContext appContext;

    /**
     * 数据类型->管道中处理器类型列表 的路由
     */
    private static final
    Map<Class<? extends PipelineContext>,
            List<Class<? extends ContextHandler<? extends PipelineContext>>>> PIPELINE_ROUTE_MAP = new HashMap<>(4);

    /*
     * 在这里配置各种上下文类型对应的处理管道：键为上下文类型，值为处理器类型的列表
     */
    static {
        PIPELINE_ROUTE_MAP.put(InstanceBuildContext.class,
                Arrays.asList(
                        InputDataPreChecker.class,
                        ModelInstanceCreator.class,
                        ModelInstanceSaver.class
                ));

        // 将来其他 Context 的管道配置
    }

    /**
     * 在 Spring 启动时，根据路由表生成对应的管道映射关系
     */
    @Bean("pipelineRouteMap")
    public Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getHandlerPipelineMap() {
        return PIPELINE_ROUTE_MAP.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::toPipeline));
    }

    /**
     * 根据给定的管道中 ContextHandler 的类型的列表，构建管道
     */
    private List<? extends ContextHandler<? extends PipelineContext>> toPipeline(
            Map.Entry<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue()
                .stream()
                .map(appContext::getBean)
                .collect(Collectors.toList());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
