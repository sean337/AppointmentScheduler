package com.seanmlee.c195.appointmentscheduler.model;

import java.time.LocalDateTime;

/**
 * Customer Object
 *
 * @author Sean lee
 */
public class Customer {


    private long id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;
    private String createdBy;
    private String lastUpdatedBy;
    private long divisionId;

    public Customer(long id, String name, String address, String postalCode,
                    String phoneNumber, LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy,
                    String lastUpdatedBy, long divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
    }

    public Customer(String name, String address, String postalCode,
                    String phoneNumber, LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy,
                    String lastUpdatedBy, long divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
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

    public long getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(long divisionId) {
        this.divisionId = divisionId;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
