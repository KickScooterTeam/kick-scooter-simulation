package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
@Data
public class TestScooter {
    private final String modelName = "__test_scooter";
    private Long serialNumber = Math.abs(new Random().nextLong());
}
