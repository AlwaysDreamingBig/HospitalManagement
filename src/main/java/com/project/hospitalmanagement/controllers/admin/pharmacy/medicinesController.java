package com.project.hospitalmanagement.controllers.admin.pharmacy;


import com.project.hospitalmanagement.controllers.database.dataBase;
import com.project.hospitalmanagement.controllers.models.medicineModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class medicinesController implements Initializable{

    public TextField search_bar;


    public Button UploadPicture;
    public TableView<medicineModel> medicines_tableView;
    public TableColumn<medicineModel, ImageView> MedicinePicture;
    public TableColumn<medicineModel, Integer> MedicineID;
    public TableColumn<medicineModel, String> MedicineName;
    public TableColumn<medicineModel, String> MedicineCategory;
    public TableColumn<medicineModel, String> MedicineCompany;
    public TableColumn<medicineModel, Date> MedicinePurchaseDate;
    public TableColumn<medicineModel, Integer> MedicinePrice;
    public TableColumn<medicineModel, Date> MedicineExpiringDate;
    public TableColumn<medicineModel, Integer> MedicineStock;
    public TableColumn<medicineModel, String> MedicineStatus;

    public TableColumn<medicineModel, Void> Action;

    ObservableList<medicineModel> medicineModelObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataBase connection = new dataBase();
        Connection connectDB = connection.connectDB();

        //SQL Query - Execute
        String medicinesViewQuery = "SELECT `MedicinePicture`, `MedicineID`, `MedicineName`, `MedicineCategory`, `MedicineCompany`, `MedicinePurchaseDate`, `MedicinePrice`, `MedicineExpiringDate`, `MedicineStock`, `MedicineStatus` FROM `medicines`";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(medicinesViewQuery);

            while(queryOutput.next()){

                Image profilePicture;
                Blob queryDoctorPicture = queryOutput.getBlob("MedicinePicture");

                if (queryDoctorPicture == null) {
                    InputStream inputStream = getClass().getResourceAsStream("/Images/drugPicture.jpg");
                    if (inputStream != null) {
                        System.out.println("Image found");
                    } else {
                        System.out.println("Image not found");
                    }
                    //assert inputStream != null;
                    assert inputStream != null;
                    profilePicture = new Image(inputStream);
                } else {
                    // Convert Blob to Image and put inside imageView
                    InputStream inputStream = queryDoctorPicture.getBinaryStream();
                    profilePicture = new Image(inputStream);
                }

                ImageView imageView = new ImageView(profilePicture);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);

                // Create a DropShadow effect
                DropShadow dropShadow = new DropShadow();
                dropShadow.setRadius(5);
                dropShadow.setColor(Color.BLACK);
                imageView.setEffect(dropShadow);


                Integer queryMedicineID = queryOutput.getInt("MedicineID");
                String queryMedicineName = queryOutput.getString("MedicineName");
                String queryMedicineCategory = queryOutput.getString("MedicineCategory");
                String queryMedicineCompany = queryOutput.getString("MedicineCompany");
                Date queryMedicinePurchaseDate = queryOutput.getDate("MedicinePurchaseDate");
                Integer queryMedicinePrice = queryOutput.getInt("MedicinePrice");
                Date queryMedicineExpiringDate = queryOutput.getDate("MedicineExpiringDate");
                Integer queryMedicineStock = queryOutput.getInt("MedicineStock");
                String queryMedicineStatus = queryOutput.getString("MedicineStatus");

                //Populate the observableList
                medicineModelObservableList.add(new medicineModel(imageView, queryMedicineID, queryMedicineName, queryMedicineCategory, queryMedicineCompany, queryMedicinePurchaseDate, queryMedicinePrice, queryMedicineExpiringDate, queryMedicineStock, queryMedicineStatus));

            }

            // the parameters are the one declared in appointmentsMode
            //PropertyValueFactory corresponds to the new appointmentsModel fields

            MedicinePicture.setCellValueFactory(new PropertyValueFactory<>("MedicinePicture"));
            MedicineID.setCellValueFactory(new PropertyValueFactory<>("MedicineID"));
            MedicineName.setCellValueFactory(new PropertyValueFactory<>("MedicineName"));
            MedicineCategory.setCellValueFactory(new PropertyValueFactory<>("MedicineCategory"));
            MedicineCompany.setCellValueFactory(new PropertyValueFactory<>("MedicineCompany"));
            MedicinePurchaseDate.setCellValueFactory(new PropertyValueFactory<>("MedicinePurchaseDate"));
            MedicinePrice.setCellValueFactory(new PropertyValueFactory<>("MedicinePrice"));
            MedicineExpiringDate.setCellValueFactory(new PropertyValueFactory<>("MedicineExpiringDate"));
            MedicineStock.setCellValueFactory(new PropertyValueFactory<>("MedicineStock"));
            MedicineStatus.setCellValueFactory(new PropertyValueFactory<>("MedicineStatus"));
            // Action.setCellFactory(param -> new ActionButtonTableCell<>());

            medicines_tableView.setItems(medicineModelObservableList);

            //Initial filtered list
            FilteredList<medicineModel> filteredData = new FilteredList<>(medicineModelObservableList, b -> true);

            search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(medicine -> {

                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();


                    if (medicine.getMedicineName().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;    //Means a match was found in Medicine column
                    } else if (medicine.getMedicineCategory().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (medicine.getMedicineCompany().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else
                        return false;   //no match found
                });
            });

            SortedList<medicineModel> sortedData = new SortedList<>(filteredData);

            //Bind sorted result with Table View
            sortedData.comparatorProperty().bind(medicines_tableView.comparatorProperty());

            //Apply filter and sorted data to the table Veiw
            medicines_tableView.setItems(sortedData);

        } catch (SQLException e) {
            Logger.getLogger(medicinesController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }

    }

}

