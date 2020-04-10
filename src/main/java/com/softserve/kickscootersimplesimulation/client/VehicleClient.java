package com.softserve.kickscootersimplesimulation.client;


import com.softserve.kickscootersimplesimulation.model.TestScooter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "vehicle-service")
public interface VehicleClient {

    @PostMapping("/scooters")
    UUID registerScooter(TestScooter scooter);

}
