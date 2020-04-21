package com.softserve.kickscootersimplesimulation.controller;

import com.softserve.kickscootersimplesimulation.dto.InitScooterDto;
import com.softserve.kickscootersimplesimulation.repository.TestScooterRepo;
import com.softserve.kickscootersimplesimulation.service.SimpleSimulationRunner;
import com.softserve.kickscootersimplesimulation.service.TestScooterService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
