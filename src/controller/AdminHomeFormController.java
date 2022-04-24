package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import security.SecurityContextHolder;

import java.io.IOException;

public class AdminHomeFormController {
    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnManageUsers;
    public Button btnBackupRestore;
    public Button btnSignOut;
    public Label lblHover;
    public Label lblGreeting;
    public AnchorPane root;


    public void initialize(){
        final String initialText = lblHover.getText();
        btnRecordAttendance.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnViewReports.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnUserProfile.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnManageUsers.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnBackupRestore.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnSignOut.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));

        btnRecordAttendance.setOnMouseExited(event -> lblHover.setText(initialText));
        btnViewReports.setOnMouseExited(event -> lblHover.setText(initialText));
        btnUserProfile.setOnMouseExited(event -> lblHover.setText(initialText));
        btnManageUsers.setOnMouseExited(event -> lblHover.setText(initialText));
        btnBackupRestore.setOnMouseExited(event -> lblHover.setText(initialText));
        btnSignOut.setOnMouseExited(event -> lblHover.setText(initialText));

        root.setOnKeyReleased(event -> {
            switch(event.getCode()){
                case F1:
                    btnRecordAttendance.fire();
                    break;
                case F12:
                    btnSignOut.fire();
                    break;
                case F5:
                    btnBackupRestore.fire();
                    break;
            }
        });

        lblGreeting.setText("Welcome " + SecurityContextHolder.getPrincipal().getName() + "!");
    }

    private void displayHoveringText(Button button){
        lblHover.setText(button.getAccessibleText());
    }


    public void btnRecordAttendance_OnAction(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/RecordAttendanceForm.fxml"));
        Scene attendanceScene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Student Attendance System: Record Attendance");
        stage.setScene(attendanceScene);
        stage.setResizable(false);
        stage.initOwner(btnRecordAttendance.getScene().getWindow());
        stage.show();

        Platform.runLater(()->{
            stage.sizeToScene();
            stage.centerOnScreen();
        });
    }

    public void btnSignOut_OnAction(ActionEvent event) throws IOException {
        SecurityContextHolder.clear();
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene loginScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(loginScene);
        stage.setTitle("Employee Attendance System: Log In");
        stage.setResizable(false);
        stage.show();

        Platform.runLater(()->{
            stage.sizeToScene();
            stage.centerOnScreen();
        });

        ((Stage)(btnSignOut.getScene().getWindow())).close();
    }

}
