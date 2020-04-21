package com.softserve.kickscootersimplesimulation.converter;

import com.softserve.kickscootersimplesimulation.dto.ScooterRawDataDto;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TestScooterToRawDataConverter implements Converter<TestScooter, ScooterRawDataDto> {

    @Override
    public ScooterRawDataDto convert(TestScooter testScooter) {
        return new ScooterRawDataDto(
                testScooter.getId(),
                testScooter.getLongitude(),
                testScooter.getLatitude(),
                testScooter.getBattery());
    }
}
