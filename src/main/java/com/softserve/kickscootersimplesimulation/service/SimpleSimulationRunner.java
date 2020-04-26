package com.softserve.kickscootersimplesimulation.service;

import com.softserve.kickscootersimplesimulation.dto.ScooterRawDataDto;
import com.softserve.kickscootersimplesimulation.dto.YNRoad;
import com.softserve.kickscootersimplesimulation.event.StartSimulationEvent;
import com.softserve.kickscootersimplesimulation.model.SimulationScenario;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import com.softserve.kickscootersimplesimulation.repository.SimulationScenarioRepo;
import com.softserve.kickscootersimplesimulation.repository.TestScooterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleSimulationRunner {

    private static final String RAW_DATA = "raw-data";

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, ScooterRawDataDto> template;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TestScooterRepo testScooterRepo;
    private final SimulationScenarioRepo scenarioRepo;
    private final ConversionService convService;

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
        //get all scooters who ping
        List<TestScooter> scooters = testScooterRepo.findAllByPing(true);
        scooters.forEach(e -> {
            var dto = convService.convert(e, ScooterRawDataDto.class);
            log.info("Send data to topic '{}': {}", RAW_DATA, dto);
            template.send(RAW_DATA, dto);
        });
    }

    public void muteScooter(UUID scooterId){
        TestScooter scooter = testScooterRepo.getOne(scooterId);
        scooter.setPing(false);
        testScooterRepo.save(scooter);
    }

    public void unmuteScooter(UUID scooterId){
        TestScooter scooter = testScooterRepo.getOne(scooterId);
        scooter.setPing(true);
        testScooterRepo.save(scooter);
    }

    public void muteAllScooters(){
        List<TestScooter> scooters = testScooterRepo.findAllByPing(true);
        for (TestScooter scooter: scooters){
            scooter.setPing(false);
            testScooterRepo.save(scooter);
        }
    }

    public void unmuteAllScooters(){
        List<TestScooter> scooters = testScooterRepo.findAllByPing(false);
        for (TestScooter scooter: scooters){
            scooter.setPing(true);
            testScooterRepo.save(scooter);
        }
    }


    @EventListener
    public void makeAndRunSimulation(StartSimulationEvent event) {
        UUID id = event.getScooterId();
        int index = 0;
        SimulationScenario scenario = event.getScenario();

        TestScooter scooter = testScooterRepo.findById(event.getScooterId()).orElseThrow();

        ArrayList<Double[]> road = getRoad(
                scooter.getLatitude(),
                scooter.getLongitude(),
                scenario.getFLat(),
                scenario.getFLong()
        );
        log.info("Getted road:" + road.toString());
        scenario.setRoutePoints(road);

        scenarioRepo.save(scenario);
        for(Double[] point: scenario.getRoutePoints()) {
            //ping
            scooter = testScooterRepo.findById(event.getScooterId()).orElseThrow();
            if(index == scenario.getDischIndex()) {
                scooter.setBattery(scooter.getBattery() - 1);
                scooter.setLatitude(point[1]);
                scooter.setLongitude(point[0]);
                scooter.setBattery(scooter.getBattery());
                testScooterRepo.saveAndFlush(scooter);
                index = 0;
            } else {
                scooter.setLatitude(point[1]);
                scooter.setLongitude(point[0]);
                scooter.setBattery(scooter.getBattery());
                testScooterRepo.saveAndFlush(scooter);
                index++;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {

            }
        }
    }

}
