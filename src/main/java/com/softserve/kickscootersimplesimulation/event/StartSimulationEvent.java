package com.softserve.kickscootersimplesimulation.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class StartSimulationEvent extends ApplicationEvent {
    private String message;
    private UUID id;

    public StartSimulationEvent(Object source, String message, UUID id) {
        super(source);
        this.message = message;
        this.id = id;
    }

}
