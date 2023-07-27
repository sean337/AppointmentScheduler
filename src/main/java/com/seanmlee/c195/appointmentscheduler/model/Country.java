package com.seanmlee.c195.appointmentscheduler.model;

import java.time.LocalDateTime;

public class Country {

    private long id;
    private String country;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdate;
    private String createdBy;
    private String lastUpdatedBy;

    public Country(long id, String country, LocalDateTime dateCreated, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy) {
        this.id = id;
        this.country = country;
        this.dateCreated = dateCreated;
        this.lastUpdate = lastUpdate;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
