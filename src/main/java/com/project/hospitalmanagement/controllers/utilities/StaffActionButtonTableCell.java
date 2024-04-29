package com.project.hospitalmanagement.controllers.utilities;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class StaffActionButtonTableCell<S> extends TableCell<S, Void> {
    private final Button addButton = new Button("Add");
    private final Button deleteButton = new Button("Delete");
    private final Button editButton = new Button("Display");
    private final Consumer<S> displayDetailsCallback;

    public StaffActionButtonTableCell(Consumer<S> displayDetailsCallback) {
        this.displayDetailsCallback = displayDetailsCallback;

        addButton.setOnAction(event -> {
            // Handle add action here

        });

        deleteButton.setOnAction(event -> {
            // Handle delete action here
        });

        editButton.setOnAction(event -> {
            S rowData = getTableView().getItems().get(getIndex());
            displayDetailsCallback.accept(rowData);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            addButton.setPrefSize(10, 10);
            deleteButton.setPrefSize(10, 10);
            editButton.setPrefSize(10, 10);
            setGraphic(new HBox(addButton, deleteButton,editButton));
        }
    }
}


