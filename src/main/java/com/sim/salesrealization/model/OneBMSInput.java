package com.sim.salesrealization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneBMSInput {

    private Long id;
    private String engineType;
    private int noOfEngines;
    private String customerSpecification;
    private String customerName;
    private double dBUNetPrice;


    @Override
    public String toString() {
        return "OneBMS{" +
                "id=" + id +
                ", engineType='" + engineType + '\'' +
                ", customerSpecification='" + customerSpecification + '\'' +
                ", customerName='" + customerName + '\'' +
                ", noOfEngines=" + noOfEngines +
                ", dBUNetPrice=" + dBUNetPrice +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCustomerSpecification() {
        return customerSpecification;
    }

    public void setCustomerSpecification(String customerSpecification) {
        this.customerSpecification = customerSpecification;
    }

    public int getNoOfEngines() {
        return noOfEngines;
    }

    public void setNoOfEngines(int noOfEngines) {
        this.noOfEngines = noOfEngines;
    }

    public double getdBUNetPrice() {
        return dBUNetPrice;
    }

    public void setdBUNetPrice(double dBUNetPrice) {
        this.dBUNetPrice = dBUNetPrice;
    }
}
