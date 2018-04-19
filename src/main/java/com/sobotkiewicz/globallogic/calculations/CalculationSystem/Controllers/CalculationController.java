package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Controllers;


import com.sobotkiewicz.globallogic.calculations.CalculationSystem.CalculationOrder;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.ResultAndDate;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Services.CalculationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Api(value="Calculations", description="Operations done on calculation orders")
@RestController
public class CalculationController {


    private final CalculationService calculationService;

    @Autowired
    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }


    @ApiOperation(value = "View a list of all existing calculations")
    @GetMapping("/calculations")
    public List<CalculationOrder> getAllCalculations(){
        return calculationService.getAllCalculations();
    }

    @ApiOperation(value = "View calculation on given ID")
    @GetMapping("/calculations/{calculationId}")
    public Optional<CalculationOrder> getCalculation(@PathVariable UUID calculationId){
        return calculationService.getCalculation(calculationId);
    }

    @ApiOperation(value = "Execute all existing calculations")
    @GetMapping("/calculations/runAll")
    public List<ResultAndDate> runAllCalculations() throws Exception {
        return calculationService.runAllCalculations();
    }

    @ApiOperation(value = "Execute all calculations in project on given ID")
    @GetMapping("/projects/{projectId}/run")
    public List<ResultAndDate> runAllCalculationsFromProject(@PathVariable UUID projectId) throws Exception {
        return calculationService.runAllCalculationsFromProject(projectId);
    }

    @ApiOperation(value = "Execute calculation on given ID")
    @GetMapping("/calculations/{calculationId}/run")
    public ResponseEntity<ResultAndDate> calculateAndSaveResults(@PathVariable UUID calculationId) throws Exception {
        ResultAndDate resultAndDate = calculationService.calculateAndSaveResults(calculationId);
        final String responseMessage ="Executed calculation, result is: " + resultAndDate.getResult() +"\nDate: " +  resultAndDate.getTimestamp();
        return new ResponseEntity(responseMessage, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Create calculation")
    @PostMapping("/calculations")
    public ResponseEntity<CalculationOrder> addCalculation(@ApiParam(value = "Mathematical expression and projectId", required = true,
            example = "{\"bodyOfOperation\": \"1+1\",\"calcProjectId\": \"0d5762af-ea58-47c1-8e66-0aa8da5b808c\"}")
           @RequestBody final CalculationOrder calculationOrder, BindingResult result) {
        if (result.hasErrors()){
            final String responeMessage = "Invalid input";
            return new ResponseEntity(responeMessage, HttpStatus.BAD_REQUEST);
        }
        else {
            calculationService.addCalculation(calculationOrder);
            final String responeMessage = "Created calculationOrder: " + calculationOrder.getCalculationId();
            return new ResponseEntity(responeMessage, HttpStatus.CREATED);
        }
    }

    @ApiOperation(value = "View all calculations from project")
    @GetMapping("/projects/{projectId}/calculations")
    public List<CalculationOrder> getAllCalculationsFromProject(@PathVariable UUID projectId){
        return  calculationService.getAllCalculationsFromProject(projectId);
    }

    @ApiOperation(value = "Delete a project on given ID")
    @DeleteMapping("/calculations/{calculationId}")
    public ResponseEntity<String> deleteCalculation(@PathVariable UUID calculationId) {
        calculationService.deleteCalculation(calculationId);
        final String responseMessage = "Calculation deleted";
        return new ResponseEntity(responseMessage, HttpStatus.OK);
    }



}
