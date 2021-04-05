package com.winway.demo.design.wwabstract;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
@Component
public abstract class AbstractMessageHandler<T> implements Runnable {

    protected String messageType;
    protected Class<T> clazz;

    public AbstractMessageHandler() {
    }

    public AbstractMessageHandler(String messageType, Class<T> clazz) {
        this.messageType = messageType;
        this.clazz = clazz;
    }

    /**
     * 初始化启动本监听器
     */
    @PostConstruct
    public void startListen()
    {
        // 启动监听
        System.out.println("初始化启动本监听器");
        new Thread(this).start();
    }

    public abstract void handle(T obj);

    @Override
    public void run() {
        handle((T) new MiaoshaRequestMessage("苹果13", "18814114937"));
    }









}
