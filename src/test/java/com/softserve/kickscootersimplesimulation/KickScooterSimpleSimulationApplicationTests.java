package com.softserve.kickscootersimplesimulation;

import com.softserve.kickscootersimplesimulation.model.TestScooter;
import com.softserve.kickscootersimplesimulation.repository.SimulationScenarioRepo;
import com.softserve.kickscootersimplesimulation.repository.TestScooterRepo;
import com.softserve.kickscootersimplesimulation.service.SimpleSimulationRunner;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SimpleSimulationRunner.class})
public class KickScooterSimpleSimulationApplicationTests {

	@Bean
	public RestTemplate template(){
		return new RestTemplate();
	}

	@MockBean
	private TestScooterRepo scooterRepo;

	@MockBean
	private SimulationScenarioRepo scenarioRepo;

	@Ignore
	//@Test
	void contextLoads() {
		when(scooterRepo.findById(UUID.randomUUID())).thenReturn(Optional.of(new TestScooter()));

	}

}
