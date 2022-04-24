package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import security.SecurityContextHolder;

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

}
