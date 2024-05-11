module com.project.hospitalmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    /*ADDED dependencies*/
    requires de.jensd.fx.glyphs.fontawesome; /*this is for the icons*/
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolbox;
    requires eu.hansolo.toolboxfx;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires org.jfree.jfreechart;

    opens com.project.hospitalmanagement to javafx.fxml;
    exports com.project.hospitalmanagement;

    /*Added packages*/
    exports com.project.hospitalmanagement.controllers;
    exports com.project.hospitalmanagement.controllers.admin;
    exports com.project.hospitalmanagement.controllers.admin.mainwindow;
    exports com.project.hospitalmanagement.controllers.admin.dashboard;
    exports com.project.hospitalmanagement.controllers.admin.doctors;
    exports com.project.hospitalmanagement.controllers.admin.patients;
    exports com.project.hospitalmanagement.controllers.admin.staff;
    exports com.project.hospitalmanagement.controllers.admin.staff.Buttons;
    exports com.project.hospitalmanagement.controllers.admin.appointments;
    exports com.project.hospitalmanagement.controllers.admin.calls;
    exports com.project.hospitalmanagement.controllers.admin.ambulances;
    exports com.project.hospitalmanagement.controllers.admin.pharmacy;
    exports com.project.hospitalmanagement.controllers.admin.billing;
    exports com.project.hospitalmanagement.controllers.admin.department;
    exports com.project.hospitalmanagement.controllers.admin.records;
    exports com.project.hospitalmanagement.controllers.admin.inventory;
    exports com.project.hospitalmanagement.controllers.admin.rooms;
    exports com.project.hospitalmanagement.controllers.general.certifications;
    exports com.project.hospitalmanagement.controllers.patient;
    exports com.project.hospitalmanagement.controllers.doctor;
    exports com.project.hospitalmanagement.controllers.alert;
    exports com.project.hospitalmanagement.controllers.database;
    exports com.project.hospitalmanagement.controllers.utilities;
    exports com.project.hospitalmanagement.controllers.factories;
    exports com.project.hospitalmanagement.controllers.factories.adminpagefactory;
    exports com.project.hospitalmanagement.controllers.models;
}