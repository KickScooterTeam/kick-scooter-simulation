package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.UUID;

@Data
@Slf4j
public class SimulationScenario {

    private UUID id;
    private Conditions specs;
    private ArrayList<Double[]> routePoints = new ArrayList<>();
    private int battery;
    private int dischIndex = 3;

}
