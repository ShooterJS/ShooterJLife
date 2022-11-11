
管道模式（Pipeline Pattern） 是责任链模式（Chain of Responsibility Pattern）的常用变体之一。在管道模式中，管道扮演着流水线的角色，将数据传递到一个加工处理序列中，数据在每个步骤中被加工处理后，传递到下一个步骤进行加工处理，直到全部步骤处理完毕。 PS：纯的责任链模式在链上只会有一个处理器用于处理数据，而管道模式上多个处理器都会处理数据。
本篇为设计模式第二篇，第一篇可见设计模式最佳套路 —— 愉快地使用策略模式

管道模式（Pipeline Pattern） 是责任链模式（Chain of Responsibility Pattern）的常用变体之一。在管道模式中，管道扮演着流水线的角色，将数据传递到一个加工处理序列中，数据在每个步骤中被加工处理后，传递到下一个步骤进行加工处理，直到全部步骤处理完毕。

PS：纯的责任链模式在链上只会有一个处理器用于处理数据，而管道模式上多个处理器都会处理数据。

何时使用管道模式
任务代码较为复杂，需要拆分为多个子步骤时，尤其是后续可能在任意位置添加新的子步骤、删除旧的子步骤、交换子步骤顺序，可以考虑使用管道模式。


愉快地使用管道模式
背景回放
最开始做模型平台的时候，创建模型实例的功能，包括：“输入数据校验 -> 根据输入创建模型实例 -> 保存模型实例到相关 DB 表”总共三个步骤，也不算复杂，所以当时的代码大概是这样的：

public class ModelServiceImpl implements ModelService {

    /**
     * 提交模型（构建模型实例）
     */
    public CommonReponse<Long> buildModelInstance(InstanceBuildRequest request) {
        // 输入数据校验
        validateInput(request);
        // 根据输入创建模型实例
        ModelInstance instance = createModelInstance(request);
        // 保存实例到相关 DB 表
        saveInstance(instance);
    }
}
然而没有过多久，我们发现表单输入数据的格式并不完全符合模型的输入要求，于是我们要加入 “表单数据的预处理”。这功能还没动手呢，又有业务方提出自己也存在需要对数据进行处理的情况（比如根据商家的表单输入，生成一些其他业务数据作为模型输入）。

所以在 “输入数据校验” 之后，还需要加入 “表单输入输出预处理” 和 “业务方自定义数据处理（可选）”。这个时候我就面临一个选择：是否继续通过在 buildModelInstance 中加入新的方法来实现这些新的处理步骤？好处就是可以当下偷懒，但是坏处呢：

1、ModelService 应该只用来接收 HSF 请求，而不应该承载业务逻辑，如果将 提交模型 的逻辑都写在这个类当中，违反了 单一职责，而且后面会导致 类代码爆炸

2、将来每加入一个新的处理步骤或者删除某个步骤，我就要修改 buildModelInstance 这个本应该非常内聚的方法，违反了 开闭原则

所以，为了不给以后的自己挖坑，我觉得要思考一个万全的方案。这个时候，我小脑袋花开始飞转，突然闪过了 Netty 中的 ChannelPipeline —— 对哦，管道模式，不就正是我需要的嘛！

管道模式的实现方式也是多种多样，接下来基于前面的背景，我分享一下我目前基于 Spring 实现管道模式的 “最佳套路”（如果你有更好的套路，欢迎赐教，一起讨论哦）。



定义管道处理的上下文
/**
* 传递到管道的上下文
  */
  @Getter
  @Setter
  public class PipelineContext {

  /**
    * 处理开始时间
      */
      private LocalDateTime startTime;

  /**
    * 处理结束时间
      */
      private LocalDateTime endTime;

  /**
    * 获取数据名称
      */
      public String getName() {
      return this.getClass().getSimpleName();
      }
      }
      定义上下文处理器
      /**
* 管道中的上下文处理器
  */
  public interface ContextHandler<T extends PipelineContext> {

  /**
    * 处理输入的上下文数据
    *
    * @param context 处理时的上下文数据
    * @return 返回 true 则表示由下一个 ContextHandler 继续处理，返回 false 则表示处理结束
      */
      boolean handle(T context);
      }
      为了方便说明，我们现在先定义出最早版 【提交模型逻辑】 的上下文和相关处理器：
```
/**
* 模型实例构建的上下文
  */
  @Getter
  @Setter
  public class InstanceBuildContext extends PipelineContext {

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
```
  处理器 - 输入数据校验：
```
@Component
public class InputDataPreChecker implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--输入数据校验--");

        Map<String, Object> formInput = context.getFormInput();

        if (MapUtils.isEmpty(formInput)) {
            context.setErrorMsg("表单输入数据不能为空");
            return false;
        }

        String instanceName = (String) formInput.get("instanceName");

        if (StringUtils.isBlank(instanceName)) {
            context.setErrorMsg("表单输入数据必须包含实例名称");
            return false;
        }

        return true;
    }
}
```
处理器 - 根据输入创建模型实例：
```
@Component
public class ModelInstanceCreator implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--根据输入数据创建模型实例--");

        // 假装创建模型实例

        return true;
    }
}
```
处理器 - 保存模型实例到相关DB表：
```
@Component
public class ModelInstanceSaver implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--保存模型实例到相关DB表--");

        // 假装保存模型实例

        return true;
    }
}
```
到这里，有个问题就出现了：应该使用什么样的方式，将同一种 Context 的 ContextHandler 串联为管道呢？思考一下：

1、给 ContextHandler 加一个 setNext 方法，每个实现类必须指定其下一个处理器。缺点也很明显，如果在当前管道中间加入一个新的 ContextHandler，那么要势必要修改前一个 ContextHandler 的 setNext 方法；另外，代码是写给人阅读的，这样做没法一眼就直观的知道整个管道的处理链路，还要进入到每个相关的 ContextHandler 中去查看才知道。

2、给 ContextHandler 加上 @Order 注解，根据 @Order 中给定的数字来确定每个 ContextHandler 的序列，一开始时每个数字间隔的可以大些（比如 10、20、30），后续加入新的 ContextHandler 时，可以指定数字为 （11、21、31）这种，那么可以避免上面方案中要修改代码的问题，但是仍然无法避免要进入每个相关的 ContextHandler 中去查看才能知道管道处理链路的问题。

3、提前写好一份路由表，指定好 ”Context -> 管道“ 的映射（管道用 List 来表示），以及管道中处理器的顺序 。Spring 来根据这份路由表，在启动时就构建好一个 Map，Map 的键为 Context 的类型，值为 管道（即 List）。这样的话，如果想知道每个管道的处理链路，直接看这份路由表就行，一目了然。缺点嘛，就是每次加入新的 ContextHandler 时，这份路由表也需要在对应管道上进行小改动 —— 但是如果能让阅读代码更清晰，我觉得这样的修改是值得的、可接受的~

bc74eee50e3e89dca430e77003b17321.gif

构建管道路由表
基于 Spring 的 Java Bean 配置，我们可以很方便的构建管道的路由表：
```
/**
* 管道路由的配置
  */
  @Configuration
  public class PipelineRouteConfig implements ApplicationContextAware {

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

  private ApplicationContext appContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
  appContext = applicationContext;
  }
  }
```
  定义管道执行器
  最后一步，定义管道执行器。管道执行器 根据传入的上下文数据的类型，找到其对应的管道，然后将上下文数据放入管道中去进行处理。
```
/**
* 管道执行器
  */
  @Component
  public class PipelineExecutor {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
    * 引用 PipelineRouteConfig 中的 pipelineRouteMap
      */
      @Resource
      private Map<Class<? extends PipelineContext>,
      List<? extends ContextHandler<? super PipelineContext>>> pipelineRouteMap;

  /**
    * 同步处理输入的上下文数据<br/>
    * 如果处理时上下文数据流通到最后一个处理器且最后一个处理器返回 true，则返回 true，否则返回 false
    *
    * @param context 输入的上下文数据
    * @return 处理过程中管道是否畅通，畅通返回 true，不畅通返回 false
      */
      public boolean acceptSync(PipelineContext context) {
      Objects.requireNonNull(context, "上下文数据不能为 null");
      // 拿到数据类型
      Class<? extends PipelineContext> dataType = context.getClass();
      // 获取数据处理管道
      List<? extends ContextHandler<? super PipelineContext>> pipeline = pipelineRouteMap.get(dataType);

      if (CollectionUtils.isEmpty(pipeline)) {
      logger.error("{} 的管道为空", dataType.getSimpleName());
      return false;
      }

      // 管道是否畅通
      boolean lastSuccess = true;

      for (ContextHandler<? super PipelineContext> handler : pipeline) {
      try {
      // 当前处理器处理数据，并返回是否继续向下处理
      lastSuccess = handler.handle(context);
      } catch (Throwable ex) {
      lastSuccess = false;
      logger.error("[{}] 处理异常，handler={}", context.getName(), handler.getClass().getSimpleName(), ex);
      }

           // 不再向下处理
           if (!lastSuccess) { break; }
      }

      return lastSuccess;
      }
      }
  ```
      使用管道模式
      此时，我们可以将最开始的 buildModelInstance 修改为：
```
public CommonResponse<Long> buildModelInstance(InstanceBuildRequest request) {
InstanceBuildContext data = createPipelineData(request);
boolean success = pipelineExecutor.acceptSync(data);

    // 创建模型实例成功
    if (success) {
        return CommonResponse.success(data.getInstanceId());
    }

    logger.error("创建模式实例失败：{}", data.getErrorMsg());
    return CommonResponse.failed(data.getErrorMsg());
}
```
我们模拟一下模型实例的创建过程：

参数正常时：


参数出错时：


这个时候我们再为 InstanceBuildContext 加入新的两个 ContextHandler：FormInputPreprocessor（表单输入数据预处理） 和 BizSideCustomProcessor（业务方自定义数据处理）。
```
@Component
public class FormInputPreprocessor implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--表单输入数据预处理--");

        // 假装进行表单输入数据预处理

        return true;
    }
}
```
```
@Component
public class BizSideCustomProcessor implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--业务方自定义数据处理--");

        // 先判断是否存在自定义数据处理，如果没有，直接返回 true

        // 调用业务方的自定义的表单数据处理

        return true;
    }
}
```
此时 buildModelInstance 不需要做任何修改，我们只需要在 “路由表” 里面，将这两个 ContextHandler 加入到 InstanceBuildContext 关联的管道中，Spring 启动的时候，会自动帮我们构建好每种 Context 对应的管道：



加入新的处理器

再模拟一下模型实例的创建过程：



异步处理
管道执行器 PipelineExecutor 中，acceptSync 是个同步的方法。

小蜜：看名字你就知道你悄悄埋伏笔了。



对于步骤繁多的任务，很多时候我们更需要的是异步处理，比如某些耗时长的定时任务。管道处理异步化非常的简单，我们先定义一个线程池，比如：

```
    <!-- 专门用于执行管道任务的线程池 -->
    <bean id="pipelineThreadPool"
    class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
    <property name="corePoolSize" value="4" /> <!-- 核心线程数 -->
    <property name="maxPoolSize" value="8" />  <!-- 最大线程数 -->
    <property name="keepAliveSeconds" value="960" />  <!-- 线程最大空闲时间/秒（根据管道使用情况指定）-->
    <property name="queueCapacity" value="256" />     <!-- 任务队列大小（根据管道使用情况指定）-->
    <property name="threadNamePrefix" value="pipelineThreadPool" />
    <property name="rejectedExecutionHandler">
    <bean class="java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy" />
    </property>
    </bean>
```
然后在 PipelineExecutor 中加入异步处理的方法：
```
/**
* 管道线程池
  */
  @Resource
  private ThreadPoolTaskExecutor pipelineThreadPool;

/**
* 异步处理输入的上下文数据
*
* @param context  上下文数据
* @param callback 处理完成的回调
  */
  public void acceptAsync(PipelineContext context, BiConsumer<PipelineContext, Boolean> callback) {
  pipelineThreadPool.execute(() -> {
  boolean success = acceptSync(context);

       if (callback != null) {
           callback.accept(context, success);
       }
  });
  }
```
  通用处理
  比如我们想记录下每次管道处理的时间，以及在处理前和处理后都打印相关的日志。那么我们可以提供两个通用的 ContextHandler，分别放在每个管道的头和尾：
```
@Component
public class CommonHeadHandler implements ContextHandler<PipelineContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(PipelineContext context) {
        logger.info("管道开始执行：context={}", JSON.toJSONString(context));

        // 设置开始时间
        context.setStartTime(LocalDateTime.now());

        return true;
    }
}
```
```
@Component
public class CommonTailHandler implements ContextHandler<PipelineContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(PipelineContext context) {
        // 设置处理结束时间
        context.setEndTime(LocalDateTime.now());

        logger.info("管道执行完毕：context={}", JSON.toJSONString(context));

        return true;
    }
}
```
通用头、尾处理器可以在路由表里面放置，但是每次新加一种 PipelineContext 都要加一次，好像没有必要 —— 我们直接修改下 管道执行器 PipelineExecutor 中的 acceptSync 方法：
```
@Component
public class PipelineExecutor {

    ......

    @Autowired
    private CommonHeadHandler commonHeadHandler;

    @Autowired
    private CommonTailHandler commonTailHandler;

    public boolean acceptSync(PipelineContext context) {
        ......

        // 【通用头处理器】处理
        commonHeadHandler.handle(context);

        // 管道是否畅通
        boolean lastSuccess = true;

        for (ContextHandler<? super PipelineContext> handler : pipeline) {
            try {
                // 当前处理器处理数据，并返回是否继续向下处理
                lastSuccess = handler.handle(context);
            } catch (Throwable ex) {
                lastSuccess = false;
                logger.error("[{}] 处理异常，handler={}", context.getName(), handler.getClass().getSimpleName(), ex);
            }

            // 不再向下处理
            if (!lastSuccess) { break; }
        }

        // 【通用尾处理器】处理
        commonTailHandler.handle(context);

        return lastSuccess;
    }
}
```
总结
通过管道模式，我们大幅降低了系统的耦合度和提升了内聚程度与扩展性：

* ModelService 只负责处理 HSF 请求，不用关心具体的业务逻辑
* PipelineExecutor 只做执行工作，不用关心具体的管道细节
* 每个 ContextHandler 只负责自己那部分的业务逻辑，不需要知道管道的结构，与其他ContextHandler 的业务逻辑解耦
* 新增、删除 或者 交换子步骤时，都只需要操作路由表的配置，而不要修改原来的调用代码

文章出处：
https://developer.aliyun.com/article/778865#slide-5 