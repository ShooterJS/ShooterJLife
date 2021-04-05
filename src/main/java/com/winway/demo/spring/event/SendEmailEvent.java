package com.winway.demo.spring.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SendEmailEvent extends ApplicationEvent {


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    @Getter
    private Integer id;

    public SendEmailEvent(Object source, Integer id) {
        super(source);
        this.id = id;
    }


}
