package com.softserve.kickscootersimplesimulation.converter;

import com.softserve.kickscootersimplesimulation.dto.TestScooterDto;
import com.softserve.kickscootersimplesimulation.model.TestScooter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TestScooterDtoToTestEntity implements Converter<TestScooterDto, TestScooter> {
    @Override
    public TestScooter convert(TestScooterDto testScooterDto) {
        var scooter = new TestScooter();
        scooter.setModelName(testScooterDto.getModelName());
        scooter.setSerialNumber(testScooterDto.getSerialNumber());
        return scooter;
    }
}
