package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import util.RJAlert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAdminFormController {

    private void handlePasswordStrength() {
        rect1.setFill(Color.TRANSPARENT);
        rect2.setFill(Color.TRANSPARENT);
        rect3.setFill(Color.TRANSPARENT);

        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            rect1.setFill(Color.TRANSPARENT);
            rect2.setFill(Color.TRANSPARENT);
            rect3.setFill(Color.TRANSPARENT);
            if (!newValue.trim().isEmpty() && newValue.trim().length() < 4) {
                rect1.setFill(Paint.valueOf("#ff1f1f"));
            } else if (newValue.trim().length() == 4) {
                rect1.setFill(Paint.valueOf("#ff1f1f"));
                rect2.setFill(Paint.valueOf("#ffef21"));
            } else if (newValue.trim().length() >= 4) {
                rect1.setFill(Paint.valueOf("#ff1f1f"));
                rect2.setFill(Paint.valueOf("#ffef21"));
                rect3.setFill(Paint.valueOf("#21ff56"));
            }
        });
    }

    public void btnCreateAccount_OnAction(ActionEvent event) {
        if (!isValidated()) {
            return;
        }

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO user (name, username, password, role) VALUES (?,?,?,?)");
            stm.setString(1, txtName.getText().trim());
            stm.setString(2, txtUserName.getText().trim());
            stm.setString(3, txtPassword.getText().trim());
            stm.setString(4, "ADMIN");
            stm.executeUpdate();

            new RJAlert(Alert.AlertType.INFORMATION, "Account has been created successfully", "Account Created", "Success").showAndWait();



            ((Stage)(btnCreateAccount.getScene().getWindow())).close();
        } catch (SQLException | IOException e) {
            new RJAlert(Alert.AlertType.WARNING, "Something went wrong, please try again", "Oops..!", "Failed").show();
            e.printStackTrace();
        }
    }

    private boolean isValidated() {
        String name = txtName.getText().trim();
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (!name.matches("[A-Za-z ]+")) {
            new RJAlert(Alert.AlertType.ERROR, "Please enter valid name", "Validation Error").show();
            txtName.selectAll();
            txtName.requestFocus();
            return false;
        } else if (username.length() < 4) {
            new RJAlert(Alert.AlertType.ERROR, "Username should be at least 4 characters long", "Validation Error").show();
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        } else if (!username.matches("[A-Za-z0-9]+")) {
            new RJAlert(Alert.AlertType.ERROR, "Username can contain only characters and digits", "Validation Error").show();
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        } else if (password.length() < 4) {
            new RJAlert(Alert.AlertType.ERROR, "Password should be at least 4 characters long", "Validation Error").show();
            txtPassword.selectAll();
            txtPassword.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            new RJAlert(Alert.AlertType.ERROR, "Password mismatch", "Validation Error").show();
            txtConfirmPassword.selectAll();
            txtConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }



}
