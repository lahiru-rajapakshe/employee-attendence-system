package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import security.SecurityContextHolder;

import java.io.IOException;
import java.util.Date;

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

    public void btnSignOut_OnAction(ActionEvent event) throws IOException {
        SecurityContextHolder.clear();
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));
        Scene loginScene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(loginScene);
        stage.setTitle("Student Attendance System: Log In");
        stage.setResizable(false);
        stage.show();

        Platform.runLater(()->{
            stage.sizeToScene();
            stage.centerOnScreen();
        });

        ((Stage)(btnSignOut.getScene().getWindow())).close();
    }

    public void btnUserProfile_OnAction(ActionEvent event) {

    }


    public void btnViewReports_OnAction(ActionEvent event) {

    }

    public void showdate(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event -> {
            time.setText(String.format(" %1$tH:%1$tM", new Date()));
        })));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnHomeClose_OnAction(ActionEvent event) {
        Platform.exit();
    }

}
