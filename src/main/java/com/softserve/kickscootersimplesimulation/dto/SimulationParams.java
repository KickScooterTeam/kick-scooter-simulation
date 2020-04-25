package com.softserve.kickscootersimplesimulation.dto;

import com.softserve.kickscootersimplesimulation.model.Condition;
import lombok.Data;

import java.util.UUID;

@Data
public class SimulationParams {
    private UUID scooterId;
    private double destLat;
    private double destLon;
    private Condition condition = Condition.NORMAL;
    private int dischIndex = 3;

}
