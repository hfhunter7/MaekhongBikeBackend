package com.gaopai.maekhongbikebackend.bean.trip;

public class CreateTripBean {
    private String trip_name;
    private String description;

    public CreateTripBean(){

    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
