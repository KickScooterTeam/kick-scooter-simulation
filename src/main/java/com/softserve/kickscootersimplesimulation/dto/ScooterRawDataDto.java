package com.softserve.kickscootersimplesimulation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ScooterRawDataDto {
    UUID id;
    double longitude;
    double latitude;
    int battery;
}
