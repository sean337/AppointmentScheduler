package com.seanmlee.c195.appointmentscheduler.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateTimeUtil {


    private static final DateTimeFormatter SQL_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");

    public static String formatForSql(LocalDateTime dateTime) {
        return SQL_TIMESTAMP_FORMAT.format(dateTime);
    }

    public static DateTimeFormatter getSqlTimestampFormat(){
        return SQL_TIMESTAMP_FORMAT;
    }

    public static LocalDateTime parseDateTime(DatePicker datePicker, ComboBox comboBox) {
        LocalDate date = datePicker.getValue();
        LocalTime time = LocalTime.parse((CharSequence) comboBox.getValue());
        return LocalDateTime.of(date, time);
    }


    public static ObservableList<String> generateTimeSlots() {
        ObservableList<String> times = FXCollections.observableArrayList();
        for (int hour = 8; hour < 22; hour++) {
            for (int min = 0; min < 60; min += 15) {
                times.add(String.format("%02d:%02d", hour, min));
            }
        }
        return times;
    }

    public static void getDate(){

    }
}
