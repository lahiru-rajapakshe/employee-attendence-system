package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class LoginFormController {
    public Button btnSignIn;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public ImageView closeImg;
    public JFXButton btnClose;

    public void btnClose_OnAction(ActionEvent event) {
    }

    public void btnSignIn_OnAction(ActionEvent event) {
        if (!isValidated()){
            new DepAlert(Alert.AlertType.ERROR, "Invalid username or password", "Invalid credentials").show();



            txtUserName.requestFocus();
            txtUserName.selectAll();
            return;
        }

    }
}
