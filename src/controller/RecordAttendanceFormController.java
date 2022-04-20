package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import security.SecurityContextHolder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

public class RecordAttendanceFormController {
    public TextField txtStudentID;
    public ImageView imgProfile;
    public Button btnIn;
    public Button btnOut;
    public Label lblDate;
    public Label lblID;
    public Label lblName;
    public Label lblStatus;
    public Label lblStudentName;
    public AnchorPane root;
    private PreparedStatement stmSearchStudent;
    private Student student;

    public void initialize() {
        btnIn.setDisable(true);
        btnOut.setDisable(true);
        lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event -> {
            lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));
        })));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        updateLatestAttendance();

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            stmSearchStudent = connection.prepareStatement("SELECT * FROM student WHERE id=?");
        } catch (Exception e) {
            new DepAlert(Alert.AlertType.WARNING, "Failed to connect with DB", "Connection Error", "Error").show();
            e.printStackTrace();
            Platform.runLater(() -> {
                ((Stage) (btnIn.getScene().getWindow())).close();
            });
        }

        root.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case F10:
                    btnIn.fire();
                    break;
                case ESCAPE:
                    btnOut.fire();
                    break;
            }
        });
    }

    public void btnIn_OnAction(ActionEvent event) {
        recordAttendance(true);
    }

    public void btnOut_OnAction(ActionEvent event) {
        recordAttendance(false);
    }

    private void recordAttendance(boolean in) {
        Connection connection = DBConnection.getInstance().getConnection();

        /* Check last record status */
        try {
            String lastStatus = null;
            PreparedStatement stm = connection.
                    prepareStatement("SELECT status, date FROM attendance WHERE student_id=? ORDER BY date DESC LIMIT 1");
            stm.setString(1, student.id);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                lastStatus = rst.getString("status");
            }

            if ((lastStatus != null && lastStatus.equals("IN") && in) ||
                    (lastStatus != null && lastStatus.equals("OUT") && !in)) {
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/AlertForm.fxml"));
                AnchorPane root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                AlertFormController controller = fxmlLoader.getController();
                SimpleBooleanProperty record = new SimpleBooleanProperty(false);
                controller.initData(student.id, lblStudentName.getText(),
                        rst.getTimestamp("date").toLocalDateTime(), in, record);
                stage.setResizable(false);
                stage.setTitle("Alert! Horek");
                stage.sizeToScene();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(this.root.getScene().getWindow());
                stage.centerOnScreen();
                stage.showAndWait();
                if (!record.getValue()) return;
            }

            PreparedStatement stm2 = connection.
                    prepareStatement("INSERT INTO attendance (date, status, student_id, username) VALUES (NOW(),?,?,?)");
            stm2.setString(1, in ? "IN" : "OUT");
            stm2.setString(2, student.id);
            stm2.setString(3, SecurityContextHolder.getPrincipal().getUsername());
            if (stm2.executeUpdate() != 1) {
                throw new RuntimeException("Failed to add the attendance");
            }
            //new Thread(() -> sendSMS(in)).start();
            txtStudentID.clear();
            txtStudentID_OnAction(null);
            updateLatestAttendance();

        } catch (Throwable e) {
            e.printStackTrace();
            new DepAlert(Alert.AlertType.ERROR, "Failed to save the attendance, try again",
                    "Failure", "Error", ButtonType.OK).show();
        }}

    private void sendSMS(boolean in) {
        try {
            URL url = new URL("https://api.smshub.lk/api/v2/send/single");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "A4n2dulq9EZvFVNHsO4oufAqBtQTr4tj7WZYgkqskF9L9EQrcUhSm10OtBw3UOnVGoScqBmXIIKM5eCGsLdyu4CUmWfxwXHM10pMUtGKZ5OPhFSauEXWUqpfq07YW9j2Fu3Hf36uz8ShSVBNHtS9TKInYLRGrveo6a7MO5ftvU4t6Ha5T65wahDEdh1OCkghJclAH1Nj6ExcMm5BOWzseFlMALqIcmKc0xRCd02qhNVbv6WQRBEhlPnnogWG7yWwtxtFF087xkaGlhcyT6dCMXMmJyM6sa7cSNCyxaZA5OK3Oi4PA798pswC9tlvBMbLPPqKxZ3N7pz8U2S9thdMKJyQJsG6FFXBaIlE65fvdnMmvsdQHSx8Enee6utzGigczFUclT6be6RQDqksUPmNdOxlXrU6F3WOjUdZ7v3BodGHyvfW2yh6XsDSpVF7OaYnIbB0kgxUAV55bYpy6UdrK2j2QTwlEvPcKg59eZen9wdLtwJjEPxCXW2hEQeSXgelpB2rgvAGbUnB9ViqMJjCVc0ovA4sArNs0stcR4Qiw0o2nyhcL51SQb8Adm8axh2m2Tk49tdJzZu8hLB2jZ1mmTMcQwQqr1aeU7mv8Uh2S79wOFBKA2yhTCf1kG73KgaK8ieltQVRBNeJwR011gT5k72udiS3COQHNBE03WDfhdobVpT9oU7d5wLmGMh30AsAsNrq9beLWo3qU12hzfYamjbyWyDPlru68FFciVpAG1jo4oYCfz8lUCy1UPv6CL0MuTsm0uNfbxnIy5MttCfFHJQRXHDivPFHjRSaS0mVv8AmXzRtjBIlM4oM8uJohBZm4lZLeUTnXHChANxolPo3NOMkjbsz826uuraeyzuCaISGdt8C9GpoV8vSmjvBdDI4OW7ZcwBr1TJsO3sAZY9FpbyKprhAqRfzIxtNqpJuQgpVBnVCQNDIUkPWwzhjGhi9ozhaD4YSOjo3lU3zluzvS8chT8fQJf0aCfEqU4rF2Cesmto3ROTibGFfu1XxjkAv");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String payload = String.format("{\n" +
                            "  \"message\": \"%s\",\n" +
                            "  \"phoneNumber\": \"%s\"\n" +
                            "}",
                    student.name + " has " + (in ? "entered to " : "exited from") + "IJSE at : " + LocalDateTime.now(),
                    student.guardianContact);
            connection.getOutputStream().write(payload.getBytes());
            connection.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateLatestAttendance(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT s.id, s.name, a.status, a.date FROM student s INNER JOIN attendance a on s.id = a.student_id\n" +
                    "ORDER BY date DESC LIMIT 1");
            if (rst.next()){
                lblID.setText("ID: " + rst.getString("id"));
                lblName.setText("Name: " + rst.getString("name"));
                lblStatus.setText("Date: " + rst.getString("date") + " - " + rst.getString("status"));
            }else{
                /* Fresh start */
                lblID.setText("ID: -");
                lblName.setText("Name: -");
                lblStatus.setText("Date: -");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
