package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import util.RJAlert;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class BackupAndRestoreFormController {
    public Button btnBackup;

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose backup location");
        fileChooser.setInitialFileName(LocalDate.now() + "-sas-bak");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup files (*.dep8bak)", "*.dep8bak"));
        File file = fileChooser.showSaveDialog(btnBackup.getScene().getWindow());


    }


}
