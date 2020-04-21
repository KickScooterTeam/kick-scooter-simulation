package com.softserve.kickscootersimplesimulation.service;

import com.softserve.kickscootersimplesimulation.dto.ScooterRawDataDto;
import com.softserve.kickscootersimplesimulation.dto.YNRoad;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import com.softserve.kickscootersimplesimulation.repository.TestScooterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleSimulationRunner {

    private static final String RAW_DATA = "raw-data";

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, ScooterRawDataDto> template;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TestScooterRepo testScooterRepo;
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
        //ping
        List<TestScooter> scooters = testScooterRepo.findAllByPing(true);
        scooters.forEach(e -> {
            var dto = convService.convert(e, ScooterRawDataDto.class);
            log.info("Send data to topic '{}': {}", RAW_DATA, dto);
            template.send(RAW_DATA, dto);
        });


    }


}
