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

            new DepAlert(Alert.AlertType.INFORMATION, "Account has been created successfully", "Account Created", "Success").showAndWait();

            /* Let's redirect to the Login Form */
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
            Scene loginScene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Student Attendance System: Log In");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
            Platform.runLater(() -> primaryStage.sizeToScene());

            ((Stage)(btnCreateAccount.getScene().getWindow())).close();
        } catch (SQLException | IOException e) {
            new DepAlert(Alert.AlertType.WARNING, "Something went wrong, please try again", "Oops..!", "Failed").show();
            e.printStackTrace();
        }
    }
}
