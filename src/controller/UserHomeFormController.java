package controller;

import com.jfoenix.controls.JFXButton;
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

public class UserHomeFormController {
    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnSignOut;
    public Label lblHover;
    public Label lblGreeting;
    public AnchorPane root;
    public AnchorPane mainlbl;
    public Label time;
    public AnchorPane dateAnc;
    public AnchorPane dateAnc2;
    public JFXButton btnHomeClose;
    public JFXButton btnMinimize;
    private int minute;
    private int hour;
    private int second;


    public void initialize(){

        final String initialText = lblHover.getText();
        btnRecordAttendance.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnViewReports.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnUserProfile.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));
        btnSignOut.setOnMouseEntered(event -> displayHoveringText((Button) event.getSource()));

        btnRecordAttendance.setOnMouseExited(event -> lblHover.setText(initialText));
        btnViewReports.setOnMouseExited(event -> lblHover.setText(initialText));
        btnUserProfile.setOnMouseExited(event -> lblHover.setText(initialText));
        btnSignOut.setOnMouseExited(event -> lblHover.setText(initialText));

        lblGreeting.setText("Hello " + SecurityContextHolder.getPrincipal().getName() + " !");

        root.setOnKeyReleased(event -> {
            switch(event.getCode()){
                case F1:
                    btnRecordAttendance.fire();
                    break;
                case F12:
                    btnSignOut.fire();
                    break;
            }
        });

      //  showdate();
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

}
