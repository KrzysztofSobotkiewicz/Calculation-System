package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Services;

import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalculationOrder;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance.CalculationRepository;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance.ResultAndDateRepository;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.ResultAndDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultAndDateService {



    private final ResultAndDateRepository resultAndDateRepository;
    private final CalculationRepository calculationRepository;

    @Autowired
    public ResultAndDateService(ResultAndDateRepository resultAndDateRepository, CalculationRepository calculationRepository) {
        this.resultAndDateRepository = resultAndDateRepository;
        this.calculationRepository = calculationRepository;
    }

    public List<ResultAndDate> getResults(UUID calculationId) {
        final List<CalculationOrder> calculationOrders = calculationRepository.findAll();
        final Optional<CalculationOrder> calculationOrder = calculationRepository.findById(calculationId);
        return  calculationOrder.get().getResultsAndDates();
    }
    public void addResultAndDate ( final double result, final UUID calculationId) {
        ResultAndDate resultAndDate = new ResultAndDate( result, calculationId);
        resultAndDateRepository.save(resultAndDate);
    }

}
