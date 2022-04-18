package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RJAlert extends Alert {

    public RJAlert(AlertType alertType) {
        super(alertType);
        init();
    }

    public RJAlert(AlertType alertType, String contentText, String headerText, String title, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        setTitle(title);
        setHeaderText(headerText);
        init();
    }

    public RJAlert(AlertType alertType, String contentText, String title, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        setTitle(title);
        setHeaderText(title);
        init();
    }

    public RJAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
        init();
    }

    private void init(){
        Image image = null;
        switch (getAlertType()){
            case ERROR:
                if (getTitle() == null) setTitle("Error");
                if (getHeaderText() == null) setHeaderText("Error");
                image = new Image("/view/assets/cancel.png");
                break;
            case WARNING:
                if (getTitle() == null) setTitle("Warning");
                if (getHeaderText() == null) setTitle("Warning");
                image = new Image("/view/assets/warning.png");
                break;
            case INFORMATION:
                if (getTitle() == null) setTitle("Success");
                if (getHeaderText() == null) setTitle("Success");
                image = new Image("/view/assets/checked.png");
                break;
        }
        if (image != null){
            ImageView icon = new ImageView(image);
            icon.setFitWidth(48);
            icon.setFitHeight(48);
            setGraphic(icon);
        }
    }
}
