package controller;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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


}
