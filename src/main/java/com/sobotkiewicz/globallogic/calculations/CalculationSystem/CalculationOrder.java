package com.sobotkiewicz.globallogic.calculations.CalculationSystem;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Entity
public class CalculationOrder {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID calculationId;
    @NotNull
    private String bodyOfOperation;

    @Column(name = "calc_project_id")
    @NotNull
    private UUID calcProjectId;

    @OneToMany
    @JoinColumn(name = "calculation_id")
    private List<ResultAndDate> resultsAndDates;

    public CalculationOrder(String bodyOfOperation, UUID calcProjectId) {
        this.bodyOfOperation = bodyOfOperation;
        this.calcProjectId = calcProjectId;

    }


    public CalculationOrder() {
    }

    public List<ResultAndDate> getResultsAndDates() {
        return resultsAndDates;
    }

    public UUID getCalculationId() {
        return calculationId;
    }


    public String getBodyOfOperation() {
        return bodyOfOperation;
    }


    public UUID getCalcProjectId() {
        return calcProjectId;
    }
}
