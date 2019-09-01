package com.eduardozanela.beacondetector.entities;

import com.orm.SugarRecord;

public class DistanceEntity extends SugarRecord {

    private String uuid;
    private Double distance;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
