package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.UUID;

@Data
@Entity
public class SimulationScenario {

    @Id
    @GeneratedValue
    private Long id;

    private UUID scooterId;

    private Condition specs;

    private double sLat;

    private double sLong;

    private double fLat;

    private double fLong;

    @Transient
    private ArrayList<Double[]> routePoints = new ArrayList<>();

    private int dischIndex = 3;

}
