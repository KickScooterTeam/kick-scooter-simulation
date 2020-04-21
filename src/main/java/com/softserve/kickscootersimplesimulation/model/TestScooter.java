package com.softserve.kickscootersimplesimulation.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class TestScooter {

    @Id
    private UUID id;

    private String modelName;

    private Long serialNumber;

    private double latitude;

    private double longitude;

    private int battery;

    private boolean ping;

}
