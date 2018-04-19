package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance;


import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalcProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<CalcProject, UUID> {

}