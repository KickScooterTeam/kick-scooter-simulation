package com.softserve.kickscootersimplesimulation;

import com.softserve.kickscootersimplesimulation.dto.SimulationParams;
import com.softserve.kickscootersimplesimulation.model.SimulationScenario;
import com.softserve.kickscootersimplesimulation.service.SimpleSimulationRunner;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/simulation")
@AllArgsConstructor
public class TestController {

    private SimpleSimulationRunner runner;
    private ApplicationEventPublisher applicationEventPublisher;
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @PostMapping("/make-scenario")
    public ResponseEntity<String> giveVirtualScooter(@RequestBody SimulationParams params) {
        ArrayList<Double[]> road = runner.getRoad(params.getStLat(), params.getStLon(), params.getFinLat(), params.getFinLon());
        var scenario = new SimulationScenario();
        scenario.setRoutePoints(road);
        scenario.setBattery(params.getBattery());
        UUID id = runner.buildTestScooter(params.getStLat(), params.getStLon(), params.getBattery(), scenario);
        applicationEventPublisher.publishEvent(new StartPingEvent(this, "Start ping"));
        return ResponseEntity.ok("New test scooter registered, scooter details:" + id);
    }

    @GetMapping("/simulate")
    public ResponseEntity<String> startSimulation(){

        applicationEventPublisher.publishEvent(new StartSimulationEvent(this, "Start simulation"));
        return ResponseEntity.ok("Started");
    }

    @EventListener
    public String stopTransferStaticData (ApplicationReadyEvent event){
        postProcessor.postProcessBeforeDestruction(runner, "scheduledTasks");
        return "OK";
    }

    @EventListener
    public String startStaticData(StartPingEvent event) {
        postProcessor.postProcessAfterInitialization(runner, "scheduledTasks");
        return "OK";
    }

    @EventListener
    public String stopTransferStaticData (StartSimulationEvent event){
        postProcessor.postProcessBeforeDestruction(runner, "scheduledTasks");
        return "OK";
    }

}
