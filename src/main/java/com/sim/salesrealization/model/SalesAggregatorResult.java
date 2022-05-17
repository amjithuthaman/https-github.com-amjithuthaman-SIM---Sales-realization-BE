package com.sim.salesrealization.model;

//Aggregate orders by Window and Product

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesAggregatorResult {
    private Double sales = 0.0;

    //private TreeSet<OneBMSInput> inputs = new TreeSet<>();



    public SalesAggregatorResult add(Double value) {
        sales += value;
        return this;
    }

    public SalesAggregatorResult process(OneBMSInput value) {
        sales += (value.getdBUNetPrice()*value.getNoOfEngines());
        return this;
    }

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    /*private void addInputs(OneBMSInput input) {
        inputs.add(input);
    }*/
}
