package com.eduardozanela.beacondetector.entities;

import com.eduardozanela.beacondetector.model.Distance;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PositionEntity extends SugarRecord {

    private String lat;
    private String lng;
    private Date createdDate;
    private List<DistanceEntity> distances;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<DistanceEntity> getDistances() {
        if (distances == null){
            return new ArrayList<>();
        }
        return this.distances;
    }
}
