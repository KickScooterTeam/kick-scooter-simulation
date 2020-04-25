package com.softserve.kickscootersimplesimulation.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StartPingEvent extends ApplicationEvent {
    private String message;

    public StartPingEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
