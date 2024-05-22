package com.project.hospitalmanagement.controllers.general.lists;

import com.project.hospitalmanagement.controllers.models.staffModel;
import javafx.scene.control.ListCell;

public class staffListCell extends ListCell<staffModel> {

    @Override
    protected void updateItem(staffModel item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.getStaffName());
            setGraphic(item.getStaffPicture());
        }
    }
}

