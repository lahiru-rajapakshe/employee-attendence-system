package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SplashScreenFormController {

    public Label lblStatus;
    private final SimpleObjectProperty<File> fileProperty = new SimpleObjectProperty<>();

    public void initialize() {
        establishDBConnection();
    }

    private void establishDBConnection() {
        lblStatus.setText("Establishing DB Connection..");

        new Thread(() -> {

            try {
               // sleep(800);
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeeMSys", "root", "mysql");

                Platform.runLater(() -> lblStatus.setText("Setting up the UI.."));
             //   sleep(800);

                Platform.runLater(() -> {
                //    loadLoginForm(connection);
                });

            } catch (SQLException | ClassNotFoundException e) {

                /* Let's find out whether the DB exists or not */
                if (e instanceof SQLException && ((SQLException) e).getSQLState().equals("42000")) {
              //      Platform.runLater(this::loadImportDBForm);
                } else {
             //       shutdownApp(e);
                }
            }

        }).start();
    }
}
