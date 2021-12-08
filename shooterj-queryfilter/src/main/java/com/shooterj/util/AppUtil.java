package com.shooterj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class AppUtil implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AppUtil.class);

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext _context)
            throws BeansException {
        context = _context;
    }

    /**
     * 获取spring容器上下文。
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicaitonContext() {
        return context;
    }



    /**
     * 获取Spring容器的Bean
     *
     * @param beanClass
     * @return T
     * @throws
     * @since 1.0.0
     */
    public static <T> T getBean(Class<T> beanClass) {
        try {
            return context.getBean(beanClass);
        } catch (Exception ex) {
            LOGGER.debug("getBean:" + beanClass + "," + ex.getMessage());
        }
        return null;
    }





}
