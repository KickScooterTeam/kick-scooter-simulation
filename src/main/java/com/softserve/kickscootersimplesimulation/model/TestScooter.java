package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor
@Data
public class TestScooter {
    private final String MODEL_NAME = "__test_scooter";
    private Long serial  = Math.abs(new Random().nextLong());
}
