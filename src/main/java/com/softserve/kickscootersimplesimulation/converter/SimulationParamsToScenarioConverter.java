package com.softserve.kickscootersimplesimulation.converter;

import com.softserve.kickscootersimplesimulation.dto.SimulationParams;
import com.softserve.kickscootersimplesimulation.model.SimulationScenario;
import org.springframework.core.convert.converter.Converter;

public class SimulationParamsToScenarioConverter implements Converter<SimulationParams, SimulationScenario> {
    @Override
    public SimulationScenario convert(SimulationParams simulationParams) {
        var scenario = new SimulationScenario();
        scenario.setDischIndex(simulationParams.getDischIndex());
        scenario.setFLong(simulationParams.getDestLon());
        scenario.setFLat(simulationParams.getDestLat());
        scenario.setScooterId(simulationParams.getScooterId());
        return scenario;
    }
}
