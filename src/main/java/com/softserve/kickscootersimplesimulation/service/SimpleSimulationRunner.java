package com.softserve.kickscootersimplesimulation.service;

import com.softserve.kickscootersimplesimulation.StartPingEvent;
import com.softserve.kickscootersimplesimulation.StartSimulationEvent;
import com.softserve.kickscootersimplesimulation.client.VehicleClient;
import com.softserve.kickscootersimplesimulation.model.ScooterRawDataDto;
import com.softserve.kickscootersimplesimulation.model.SimulationScenario;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import com.softserve.kickscootersimplesimulation.model.YNRoad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleSimulationRunner implements SimulationRunner {

    private static final String RAW_DATA = "raw-data";

    private final RestTemplate restTemplate;
    private final VehicleClient vehicleClient;
    private final KafkaTemplate<String, ScooterRawDataDto> template;
    private final ApplicationEventPublisher applicationEventPublisher;
    private ScooterRawDataDto rawDto;       //global state, needs to be resolved
    private SimulationScenario scenario;    //global state, needs to be resolved

    @Value("${service-token}")
    private String bearerToken;

    @Override
    public UUID buildTestScooter(double stLat, double stLon, int battery, SimulationScenario scenario) {
        var scooter = new TestScooter();
        UUID id = vehicleClient.registerScooter(bearerToken,scooter);
        rawDto = new ScooterRawDataDto(id, stLon, stLat, battery);
        scenario.setId(id);
        this.scenario = scenario;
        return id;
    }

    @Override
    public ArrayList<Double[]> getRoad(double flat, double flon, double tlat, double tlon){
        YNRoad ynRoad = restTemplate.getForObject(
                "http://www.yournavigation.org/" +
                        "api/1.0/gosmore.php" +
                        "?format=geojson" +
                        "&flat=" + flat +
                        "&flon=" + flon +
                        "&tlat=" + tlat +
                        "&tlon=" + tlon +
                        "&v=motorcar" +
                        "&fast=1" +
                        "&layer=mapnik", YNRoad.class);
        Arrays.stream(ynRoad.getCoordinates()).forEach(e -> log.info("{} , {}",e[0], e[1]));
        ArrayList<Double[]> routePoints = new ArrayList<>();
        Arrays.stream(ynRoad.getCoordinates()).forEach(e -> routePoints.add(new Double[]{e[0], e[1]}));
        return routePoints;
    }


    @Scheduled(fixedRate = 1000L)
    public void sendStatusDataToTopic(){
        //ping
        log.info("Send data to topic '{}': {}", RAW_DATA, rawDto);
        template.send(RAW_DATA, rawDto);

    }

    @EventListener
    public void sendSimulation(StartSimulationEvent event) {
        UUID id = scenario.getId();
        int index = 0;
        for(Double[] point: scenario.getRoutePoints()) {
            //ping
            if(index == scenario.getDischIndex()) {
                scenario.setBattery(scenario.getBattery() - 1);
                rawDto = new ScooterRawDataDto(id, point[0], point[1], scenario.getBattery());
                index = 0;
            } else {
                rawDto = new ScooterRawDataDto(id, point[0], point[1], scenario.getBattery());
                index++;
            }
            log.info("Send data to topic '{}': {}", RAW_DATA, rawDto);
            template.send(RAW_DATA, rawDto);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {

            }
        }
        applicationEventPublisher.publishEvent(new StartPingEvent(this, "Start ping"));
    }

}
