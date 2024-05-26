package com.project.hospitalmanagement.controllers.utilities;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class StaffActionButtonTableCell<S> extends TableCell<S, Void> {
    private final Button addButton = new Button();
    private final Button deleteButton = new Button();
    private final Button editButton = new Button();
    private final Consumer<S> displayDetailsCallback;

    public StaffActionButtonTableCell(Consumer<S> displayDetailsCallback) {
        this.displayDetailsCallback = displayDetailsCallback;

        FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

        addButton.setGraphic(addIcon);
        deleteButton.setGraphic(deleteIcon);
        editButton.setGraphic(editIcon);

        addButton.getStyleClass().add("button-add");
        deleteButton.getStyleClass().add("button-delete");
        editButton.getStyleClass().add("button-edit");

        addIcon.getStyleClass().add("fontawesome-icon");
        deleteIcon.getStyleClass().add("fontawesome-icon");
        editIcon.getStyleClass().add("fontawesome-icon");


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


