package com.sobotkiewicz.globallogic.calculations.CalculationSystem;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class CalcProject {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    @NotNull
    private String name;

    @OneToMany
    @JoinColumn(name = "calc_project_id")
    private List<CalculationOrder> calculationOrders;


    public CalcProject(String name) {
        this.name=name;
        this.calculationOrders = new ArrayList<>();
    }

    public CalcProject() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CalculationOrder> getCalculationOrders() {
        return calculationOrders;
    }



}
