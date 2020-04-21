package com.softserve.kickscootersimplesimulation.dto;

import lombok.Data;

@Data
public class InitScooterDto {
    private double stLat;
    private double stLon;
    private int battery;
    private int dischIndex;
}
