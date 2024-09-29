package com.project.hospitalmanagement;

import com.project.hospitalmanagement.controllers.models.Model;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    /*public void start(Stage stage) {
        Model.getInstance().getAdminPageFactory().showAdminWindow();
    }*/

    public void start(Stage stage) {
        Model.getInstance().getpageFactory().showLoginWindow();
    }

}
