package com.softserve.kickscootersimplesimulation.client;


import com.softserve.kickscootersimplesimulation.dto.TestScooterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @PostMapping("/scooters")
    UUID registerScooter(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken, TestScooterDto scooter);

}
