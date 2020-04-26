package com.softserve.kickscootersimplesimulation.controller;

import com.softserve.kickscootersimplesimulation.dto.InitScooterDto;
import com.softserve.kickscootersimplesimulation.dto.SimulationParams;
import com.softserve.kickscootersimplesimulation.event.StartSimulationEvent;
import com.softserve.kickscootersimplesimulation.model.SimulationScenario;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import com.softserve.kickscootersimplesimulation.repository.TestScooterRepo;
import com.softserve.kickscootersimplesimulation.service.SimpleSimulationRunner;
import com.softserve.kickscootersimplesimulation.service.TestScooterService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/simulation")
@AllArgsConstructor
public class TestController {

    private SimpleSimulationRunner runner;
    private TestScooterService scooterService;
    private TestScooterRepo scooterRepo;
    private ApplicationEventPublisher applicationEventPublisher;
    private ScheduledAnnotationBeanPostProcessor postProcessor;

    @PostMapping("/build-scooter")
    public ResponseEntity<String> makeVirtualScooter(@RequestBody InitScooterDto dto){
        UUID id = scooterService.buildTestScooter(dto.getStLat(), dto.getStLon(), dto.getBattery());
        return ResponseEntity.ok("New test scooter registered, scooter id: " + id);

    }

    @PostMapping("/run-scenario")
    public ResponseEntity<String> runScenario(@RequestBody SimulationParams params) {
        TestScooter scooter = scooterRepo.findById(params.getScooterId()).orElseThrow();
        var scenario = new SimulationScenario();
        scenario.setScooterId(params.getScooterId());
        scenario.setDischIndex(params.getDischIndex());
        scenario.setFLat(params.getDestLat());
        scenario.setFLong(params.getDestLon());
        applicationEventPublisher.publishEvent(
                new StartSimulationEvent(this, "Start", params.getScooterId(), scenario));
        return ResponseEntity.ok("New scenario started, scooter id: " + params.getScooterId());
    }

    @PostMapping("/{scooterId}/mute")
    public ResponseEntity<UUID> muteScooter(@PathVariable UUID scooterId){
        runner.muteScooter(scooterId);
        return ResponseEntity.ok(scooterId);
    }

    @PostMapping("/{scooterId}/unmute")
    public ResponseEntity<UUID> unmuteScooter(@PathVariable UUID scooterId){
        runner.unmuteScooter(scooterId);
        return ResponseEntity.ok(scooterId);
    }

    @PostMapping("/muteAll")
    public ResponseEntity<String> muteAllScooters(){
        runner.muteAllScooters();
        return ResponseEntity.ok("All scooters muted");
    }

    @PostMapping("/unmuteAll")
    public ResponseEntity<String> unmuteAllScooters(){
        runner.unmuteAllScooters();
        return ResponseEntity.ok("All scooters unmuted");
    }


}
