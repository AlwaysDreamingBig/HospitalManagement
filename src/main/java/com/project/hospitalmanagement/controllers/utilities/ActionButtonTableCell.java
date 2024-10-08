package com.project.hospitalmanagement.controllers.utilities;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

public class ActionButtonTableCell<S> extends TableCell<S, Void> {
    private final Button editButton = new Button();
    private final Button deleteButton = new Button();

    public ActionButtonTableCell() {

        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

        editButton.setGraphic(editIcon);
        deleteButton.setGraphic(deleteIcon);

        deleteButton.getStyleClass().add("button-delete");
        editButton.getStyleClass().add("button-edit");

        deleteIcon.getStyleClass().add("fontawesome-icon");
        editIcon.getStyleClass().add("fontawesome-icon");

        editButton.setOnAction(event -> {
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
            editButton.setPrefSize(20, 10);
            deleteButton.setPrefSize(20, 10);
            HBox buttons = new HBox(editButton, deleteButton);
            buttons.setSpacing(5);
            setGraphic(buttons);
        }
    }
}


