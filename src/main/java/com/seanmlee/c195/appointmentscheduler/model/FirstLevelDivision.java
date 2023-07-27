package com.seanmlee.c195.appointmentscheduler.model;

import java.time.LocalDateTime;

public class FirstLevelDivision {

    private long id;
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdate;
    private String createdBy;
    private String lastUpdatedBy;
    private long countryId;

    public FirstLevelDivision(long id, String name, LocalDateTime dateCreated, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy, long countryId) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdate = lastUpdate;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }
}
