package com.softserve.kickscootersimplesimulation.service;

import com.softserve.kickscootersimplesimulation.model.SimulationScenario;

import java.util.ArrayList;
import java.util.UUID;

public interface SimulationRunner {

    UUID buildTestScooter(double stLat, double stLon, int battery, SimulationScenario scenario);

    ArrayList<Double[]> getRoad(double flat, double flon, double tlat, double tlon);
}
