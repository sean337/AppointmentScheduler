module com.seanmlee.c195.appointmentscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.seanmlee.c195.appointmentscheduler.controller to javafx.fxml;
    opens com.seanmlee.c195.appointmentscheduler.model to javafx.base;
    exports com.seanmlee.c195.appointmentscheduler;
}