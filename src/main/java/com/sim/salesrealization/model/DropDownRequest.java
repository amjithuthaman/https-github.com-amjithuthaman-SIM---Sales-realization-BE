package com.sim.salesrealization.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DropDownRequest {

    private DropDownEnum current_dropdown;

    private String selection_type;
    private String base_period;
    private String current_period;
    private String region;
    private String engine;
    private String certification;
    private String application;
    private String channel;

    public DropDownEnum getCurrent_dropdown() {
        return current_dropdown;
    }

    public void setCurrent_dropdown(DropDownEnum current_dropdown) {
        this.current_dropdown = current_dropdown;
    }

    public String getSelection_type() {
        return selection_type;
    }

    public void setSelection_type(String selection_type) {
        this.selection_type = selection_type;
    }

    public String getBase_period() {
        return base_period;
    }

    public void setBase_period(String base_period) {
        this.base_period = base_period;
    }

    public String getCurrent_period() {
        return current_period;
    }

    public void setCurrent_period(String current_period) {
        this.current_period = current_period;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
