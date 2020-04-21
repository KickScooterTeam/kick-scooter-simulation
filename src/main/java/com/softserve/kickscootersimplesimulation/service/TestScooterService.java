package com.softserve.kickscootersimplesimulation.service;

import com.softserve.kickscootersimplesimulation.client.VehicleClient;
import com.softserve.kickscootersimplesimulation.dto.TestScooterDto;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import com.softserve.kickscootersimplesimulation.repository.TestScooterRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TestScooterService {

    private VehicleClient vehicleClient;
    private TestScooterRepo testScooterRepo;
    private ConversionService convService;

    @Value("${service-token}")
    private String bearerToken;

    public UUID buildTestScooter(double stLat, double stLon, int battery) {
        var scooterDto = new TestScooterDto();
        UUID id = vehicleClient.registerScooter(bearerToken,scooterDto);
        var scooter = convService.convert(scooterDto, TestScooter.class);

        scooter.setId(id);
        scooter.setBattery(battery);
        scooter.setLongitude(stLon);
        scooter.setLatitude(stLat);

        testScooterRepo.save(scooter);

        return id;
    }

}
