package com.eduardozanela.beacondetector.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Position {

    private String id;
    private String lat;
    private String lng;
    private ZonedDateTime createdDate;
    private List<Distance> distances;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Distance> getDistances() {
        return Collections.unmodifiableList(distances);
    }

    public void addDistance(Distance distance){
        if (distances.isEmpty()){
            this.distances = new ArrayList<>();
        }
        this.distances.add(distance);
    }
}
