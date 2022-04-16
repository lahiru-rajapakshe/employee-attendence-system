package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFormController {
    public Button btnSignIn;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public ImageView closeImg;
    public JFXButton btnClose;

    public void btnClose_OnAction(ActionEvent event) {
        Platform.exit();
    }

    public void btnSignIn_OnAction(ActionEvent event) {
        if (!isValidated()){
            new RJAlert(Alert.AlertType.ERROR, "Invalid username or password", "Invalid credentials").show();



            txtUserName.requestFocus();
            txtUserName.selectAll();
            return;
        }
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT name, role FROM user WHERE username=? AND password=?");
            stm.setString(1, txtUserName.getText().trim());
            stm.setString(2, txtPassword.getText().trim());
            ResultSet rst = stm.executeQuery();

            if (rst.next()){
                SecurityContextHolder.setPrincipal(new Principal(
                        txtUserName.getText(),
                        rst.getString("name"),
                        Principal.UserRole.valueOf(rst.getString("role"))));
                String path = null;

                if (rst.getString("role").equals("ADMIN")){
                    path = "/view/AdminHomeForm.fxml";
                }else{
                    path = "/view/UserHomeForm.fxml";
                }
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(path));
                AnchorPane root = fxmlLoader.load();
                Scene homeScene = new Scene(root);
                Stage primaryStage = (Stage)(btnSignIn.getScene().getWindow());
                homeScene.getStylesheets().add("view/style/userHomeStyles.css");
                primaryStage.setScene(homeScene);
                primaryStage.setTitle("Student Attendance System: Home");
                Platform.runLater(()-> {
                    primaryStage.sizeToScene();
                    primaryStage.centerOnScreen();
                });
            }
            else{
                new RJAlert(Alert.AlertType.ERROR, "Invalid username or password", "Invalid credentials").show();
                txtUserName.requestFocus();
                txtUserName.selectAll();
            }
        } catch (Exception e) {
            new RJAlert(Alert.AlertType.WARNING, "Something went wrong, please try again", "Oops..!", "Failure").show();
            e.printStackTrace();
        }

    }


    private boolean isValidated() {
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();

        return !(username.length() < 4 || !username.matches("[A-Za-z0-9]+") || password.length() < 4);
    }
}
