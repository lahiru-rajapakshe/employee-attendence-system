package controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AlertFormController {
    public Button btnProceed;
    public Button btnCallPolice;
    public Label lblId;
    public Label lblName;
    public Label lblDate;
    public ImageView imgDanger;
    public Label lblAlert;
    public Label lblDescription;
    private SimpleBooleanProperty record;
    private MediaPlayer player;

    public void initialize() throws URISyntaxException {
        ScaleTransition st = new ScaleTransition(Duration.millis(400), imgDanger);
        st.setFromX(0.8);
        st.setFromY(0.8);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setCycleCount(-1);
        st.play();

        playSiren();
    }

    private void playSiren() throws URISyntaxException {
        Media media = new Media(this.getClass().getResource("/assets/siren.wav").toURI().toString());
        player = new MediaPlayer(media);
        Platform.runLater(()->{
            lblId.getScene().getWindow().setOnCloseRequest(event -> {
                System.out.println("Stopped..!");
                player.stop();
                player.dispose();
            });
        });
        player.play();
    }

    public void initData(String studentId, String studentName, LocalDateTime date, boolean in, SimpleBooleanProperty record){
        lblId.setText("ID: " + studentId.toUpperCase());
        lblName.setText("NAME: " + studentName.toUpperCase());
        lblDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")) + " - " + (in? "IN" : "OUT"));
        this.record = record;
    }

    public void btnProceed_OnAction(ActionEvent actionEvent) {
        record.setValue(true);
        player.stop();
        player.dispose();
        ((Stage)(btnProceed.getScene().getWindow())).close();
    }

    public void btnCallPolice_OnAction(ActionEvent actionEvent) {
        player.stop();
        player.dispose();
        ((Stage)(btnProceed.getScene().getWindow())).close();
    }
}
