package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.UUID;

@Data
@Entity
public class SimulationScenario {

    @Id
    private Long id;

    private UUID scooterId;

    private Condition specs;

    private double fLat;

    private double fLong;

    @Transient
    private ArrayList<Double[]> routePoints = new ArrayList<>();

    private int battery;

    private int dischIndex = 3;

}
