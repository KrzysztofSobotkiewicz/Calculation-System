package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Services;


import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalcProject;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalculationOrder;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance.CalculationRepository;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance.ProjectRepository;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Persistance.ResultAndDateRepository;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.ResultAndDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CalculationService  {

    private final CalculationRepository calculationRepository;
    private final ProjectRepository projectRepository;
    private final ResultAndDateRepository resultAndDateRepository;

    @Autowired
    public CalculationService(final CalculationRepository calculationRepository, final ProjectRepository projectRepository, final ResultAndDateService resultAndDateService, final ResultAndDateRepository resultAndDateRepository, final ResultAndDateRepository resultAndDateRepository1) {
        this.calculationRepository = calculationRepository;
        this.projectRepository = projectRepository;
        this.resultAndDateRepository = resultAndDateRepository1;
    }

    public List<CalculationOrder> getAllCalculations() {
        List<CalculationOrder> calculationOrders = new ArrayList<>();
        calculationRepository.findAll().forEach(calculationOrders::add);
        return calculationOrders;
    }

    public void addCalculation(final CalculationOrder calculationOrder) {
        calculationRepository.save(calculationOrder);
    }

    public Optional<CalculationOrder> getCalculation(final UUID calculationId) {
        return calculationRepository.findById(calculationId);
    }

    public List<CalculationOrder> getAllCalculationsFromProject(final UUID projectId) {
        final List<CalcProject> projects = projectRepository.findAll();
        final Optional<CalcProject> calcProject = projectRepository.findById(projectId);
        return calcProject.get().getCalculationOrders();
    }


    public double calculate(final String bodyOfOperation) throws Exception {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        double result;

        try {
            result = Double.parseDouble(scriptEngine.eval(bodyOfOperation).toString());
        } catch (ScriptException e) {
            e.printStackTrace();
            throw new Exception("invalid bodyOfOperation");
        }

        return result;
    }

    public ResultAndDate calculateAndSaveResults(final UUID calculationId) throws Exception {
        double result = calculate(calculationRepository.findById(calculationId).get().getBodyOfOperation());
        ResultAndDate resultAndDate = new ResultAndDate(result, calculationId);
        resultAndDateRepository.save(resultAndDate);
        return resultAndDate;
    }


    public List<ResultAndDate> runAllCalculations() throws Exception {
        List<ResultAndDate> resultAndDates = new ArrayList<>();
        List<CalculationOrder> calculationOrders = new ArrayList<>();
        calculationRepository.findAll().forEach(calculationOrders::add);
        for (CalculationOrder calculation :calculationOrders) {
           resultAndDates.add(calculateAndSaveResults(calculation.getCalculationId()));
        }
        return resultAndDates;
    }

    public List<ResultAndDate> runAllCalculationsFromProject(UUID projectId) throws  Exception{
        List<ResultAndDate> resultAndDates = new ArrayList<>();
        List<CalculationOrder> calculationOrders = getAllCalculationsFromProject(projectId);
        for (CalculationOrder calculation :calculationOrders) {
            resultAndDates.add(calculateAndSaveResults(calculation.getCalculationId()));
        }
        return resultAndDates;
    }


    public void deleteCalculation(final UUID calculationId) {
        calculationRepository.deleteById(calculationId);
    }
}
