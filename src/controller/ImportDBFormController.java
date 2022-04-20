package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.File;

public class ImportDBFormController {
    public RadioButton rdoRestore;
    public ToggleGroup abc;
    public TextField txtBrowse;
    public Button btnBrowse;
    public RadioButton rdoFirstTime;
    public Button btnOK;
    private SimpleObjectProperty<File> fileProperty;

    public void initialize() {
        txtBrowse.setEditable(false);
        rdoRestore.selectedProperty().addListener((observable, oldValue, newValue) -> {
            btnOK.setDisable(txtBrowse.getText().isEmpty() && newValue);
        });
    }

    public void initFileProperty(SimpleObjectProperty<File> fileProperty) {
        this.fileProperty = fileProperty;
    }

    public void btnBrowse_OnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a backup file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Backup file", "*.dep8backup")
        );
        File file = fileChooser.showOpenDialog(btnOK.getScene().getWindow());
        txtBrowse.setText(file != null ? file.getAbsolutePath() : "");
        fileProperty.setValue(file);
    }
}
