package com.softserve.kickscootersimplesimulation;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StartSimulationEvent extends ApplicationEvent {
    private String message;

    public StartSimulationEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
