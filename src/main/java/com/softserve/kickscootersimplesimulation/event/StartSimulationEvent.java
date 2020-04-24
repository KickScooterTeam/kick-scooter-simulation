package com.softserve.kickscootersimplesimulation.event;

import com.softserve.kickscootersimplesimulation.model.SimulationScenario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
@Setter
public class StartSimulationEvent extends ApplicationEvent {
    private String message;
    private UUID scooterId;
    private SimulationScenario scenario;

    public StartSimulationEvent(Object source, String message, UUID scooterId, SimulationScenario scenario) {
        super(source);
        this.message = message;
        this.scooterId = scooterId;
        this.scenario = scenario;
    }

}
