package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance;

import com.sobotkiewicz.globallogic.calculations.CalculationSystem.ResultAndDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface ResultAndDateRepository extends CrudRepository<ResultAndDate, Timestamp> {
    public List<ResultAndDate> findByCalculationId (UUID calculationId);
}
