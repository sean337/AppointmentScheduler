package com.seanmlee.c195.appointmentscheduler.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Month;

public class Report {
    private Month month;
    private int appointmentCount;
    private Customer customer;
    private int customerCount;
    private FirstLevelDivision division;

    private String divisionName;


    public Report(String divisionName, int customerCount) {
        this.customerCount = customerCount;
        this.divisionName = divisionName;
    }
    public Report(Customer customer, int customerCount) {
        this.customer = customer;
        this.customerCount = customerCount;
    }

    public Report(FirstLevelDivision division, int customerCount) {
        this.customerCount = customerCount;
        this.division = division;
    }

    public Report(Month month, int appointmentCount) {
        this.month = month;
        this.appointmentCount = appointmentCount;
    }


    public FirstLevelDivision getDivision() {
        return this.division;
    }

    public void setDivisionName(FirstLevelDivision division) {
        this.division = division;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getAppointmentCount() {
        return appointmentCount;
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
