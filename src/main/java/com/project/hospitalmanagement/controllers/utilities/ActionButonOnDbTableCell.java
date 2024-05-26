package com.project.hospitalmanagement.controllers.utilities;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;


public class ActionButonOnDbTableCell<S> extends TableCell<S, Void> {

    private final Button deleteButton = new Button();
    private final Button editButton = new Button();
    private final Consumer<S> deleteButtonCallback;
    private final Consumer<S> editButtonCallback;


    public ActionButonOnDbTableCell(Consumer<S> deleteButtonCallback, Consumer<S> editButtonCallback) {
        this.deleteButtonCallback = deleteButtonCallback;
        this.editButtonCallback = editButtonCallback;

        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);


        deleteButton.setGraphic(deleteIcon);
        editButton.setGraphic(editIcon);

        deleteButton.getStyleClass().add("button-delete");
        editButton.getStyleClass().add("button-edit");


        deleteIcon.getStyleClass().add("fontawesome-icon");
        editIcon.getStyleClass().add("fontawesome-icon");


        deleteButton.setOnAction(event -> {
            S rowData = getTableView().getItems().get(getIndex());
            deleteButtonCallback.accept(rowData);
        });

        editButton.setOnAction(event -> {
            S rowData = getTableView().getItems().get(getIndex());
            editButtonCallback.accept(rowData);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            deleteButton.setPrefSize(10, 10);
            editButton.setPrefSize(10, 10);
            setGraphic(new HBox(deleteButton,editButton));
        }
    }


}


