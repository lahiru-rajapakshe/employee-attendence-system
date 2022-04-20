package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
}
