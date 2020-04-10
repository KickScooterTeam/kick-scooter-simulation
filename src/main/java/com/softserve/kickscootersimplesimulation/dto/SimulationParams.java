package com.softserve.kickscootersimplesimulation.dto;

import lombok.Data;

@Data
public class SimulationParams {
    private int battery;
    private double stLat;
    private double stLon;
    private double finLat;
    private double finLon;
    private int dischIndex;
}
