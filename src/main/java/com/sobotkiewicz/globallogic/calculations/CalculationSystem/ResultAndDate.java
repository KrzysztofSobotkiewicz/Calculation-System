package com.sobotkiewicz.globallogic.calculations.CalculationSystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class ResultAndDate {


    @Id
    private Timestamp timestamp;
    private Double result;

    @Column(name = "calculation_id")
    private UUID calculationId;

    public ResultAndDate (final Double result, final UUID calculationId){
         this.result = result;
         this.timestamp = new Timestamp(System.currentTimeMillis());
         this.calculationId = calculationId;
    }

    public ResultAndDate() {
    }

    public UUID getCalculationId() {
        return calculationId;
    }
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public Double getResult() {
        return result;
    }
}
