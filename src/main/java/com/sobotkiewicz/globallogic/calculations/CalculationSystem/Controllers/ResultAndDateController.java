package com.sobotkiewicz.globallogic.calculations.CalculationSystem.Controllers;

import com.sobotkiewicz.globallogic.calculations.CalculationSystem.ResultAndDate;
import com.sobotkiewicz.globallogic.calculations.CalculationSystem.Services.ResultAndDateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Api(value="Results with dates", description="Records of executed calculations")
@RestController
public class ResultAndDateController {

    private final ResultAndDateService resultAndDateService;

    @Autowired
    public ResultAndDateController(ResultAndDateService resultAndDateService) {
        this.resultAndDateService = resultAndDateService;
    }

    @ApiOperation(value = "View all records on given calculation ID")
    @GetMapping("/results/{calculationId}")
    public List<ResultAndDate> getResults (@PathVariable UUID calculationId){
        return resultAndDateService.getResults(calculationId);
    }


}
