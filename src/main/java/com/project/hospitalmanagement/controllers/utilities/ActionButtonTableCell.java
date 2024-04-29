package com.project.hospitalmanagement.controllers.utilities;


import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ActionButtonTableCell<S> extends TableCell<S, Void> {
    private final Button addButton = new Button("Add");
    private final Button removeButton = new Button("Remove");
    private final Button deleteButton = new Button("Delete");

    public ActionButtonTableCell() {
        addButton.setOnAction(event -> {
            // Handle add action here

        });

        deleteButton.setOnAction(event -> {
            // Handle delete action here
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
            removeButton.setPrefSize(10, 10);
            setGraphic(new HBox(addButton, deleteButton, removeButton));
        }
    }
}


