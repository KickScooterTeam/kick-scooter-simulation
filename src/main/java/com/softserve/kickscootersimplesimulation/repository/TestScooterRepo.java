package com.softserve.kickscootersimplesimulation.repository;

import com.softserve.kickscootersimplesimulation.model.TestScooter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestScooterRepo extends JpaRepository<TestScooter, UUID> {

    List<TestScooter> findAllByPing(boolean ping);
}
