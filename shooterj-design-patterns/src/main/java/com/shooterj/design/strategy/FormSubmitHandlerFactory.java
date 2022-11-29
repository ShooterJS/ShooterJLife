package com.shooterj.design.strategy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class FormSubmitHandlerFactory implements ApplicationContextAware, InitializingBean {


    private ApplicationContext applicationContext;

    private static final
    Map<String, FormSubmitHandler> FORM_SUBMIT_HANDLER_MAP = new HashMap(8);



    @Override
    public void afterPropertiesSet() throws Exception {
        // 将 Spring 容器中所有的 FormSubmitHandler 注册到 FORM_SUBMIT_HANDLER_MAP
        applicationContext.getBeansOfType(FormSubmitHandler.class)
                .values().stream().
                forEach(handler -> FORM_SUBMIT_HANDLER_MAP.put(handler.getSubmitType(),handler));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public FormSubmitHandler getHandler(String submitType) {
        return FORM_SUBMIT_HANDLER_MAP.get(submitType);
    }
}
