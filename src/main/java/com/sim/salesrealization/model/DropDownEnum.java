package com.sim.salesrealization.model;

public enum DropDownEnum {

    selection_type("selection_type"),
    base_period("base_period"),
    current_period("current_period"),
    region("region"),
    engine("engine"),
    certification("certification"),
    application("application"),
    channel("channel"),
    customer("customer");


    private final String value;

    DropDownEnum(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
