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

}
