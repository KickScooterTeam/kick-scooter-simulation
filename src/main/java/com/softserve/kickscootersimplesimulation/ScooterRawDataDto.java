package com.softserve.kickscootersimplesimulation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ScooterRawDataDto {
    UUID id;
    double latitude;
    double longitude;
    short battery;
}
