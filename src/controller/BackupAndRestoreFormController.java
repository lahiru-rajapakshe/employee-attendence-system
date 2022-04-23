package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import util.RJAlert;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class BackupAndRestoreFormController {
    public Button btnBackup;

    public void btnBackup_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose backup location");
        fileChooser.setInitialFileName(LocalDate.now() + "-sas-bak");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup files (*.dep8bak)", "*.dep8bak"));
        File file = fileChooser.showSaveDialog(btnBackup.getScene().getWindow());

        if (file != null) {
            ProcessBuilder mysqlDumpProcessBuilder = new ProcessBuilder("mysqldump",
                    "-h", "localhost",
                    "--port", "3306",
                    "-u", "root",
                    "-pmysql",
                    "--add-drop-database",
                    "--databases", "dep8_student_attendance");

            mysqlDumpProcessBuilder.redirectOutput(System.getProperty("os.name").equalsIgnoreCase("windows") || file.getAbsolutePath().endsWith(".dep8bak") ? file : new File(file.getAbsolutePath() + ".dep8bak"));
            try {
                Process mysqlDump = mysqlDumpProcessBuilder.start();
                int exitCode = mysqlDump.waitFor();

                if (exitCode == 0) {
                    new RJAlert(Alert.AlertType.INFORMATION, "Backup process succeeded",
                            "Success", ButtonType.OK).show();
                } else {
                    new RJAlert(Alert.AlertType.ERROR, "Backup process failed, try again!",
                            "Backup failed", "Error", ButtonType.OK).show();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void btnRestore_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a backup file to restore");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Backup files (*.dep8bak)", "*.dep8bak"));
        File file = fileChooser.showOpenDialog(btnBackup.getScene().getWindow());

        if (file != null){
            /* mysql -h localhost --port 3306 -u root -pmysql < backup-file-path */
            ProcessBuilder mysqlProcessBuilder = new ProcessBuilder("mysql",
                    "-h", "localhost",
                    "--port", "3306",
                    "-u", "root",
                    "-pmysql");
            mysqlProcessBuilder.redirectInput(file);
            try {
                Process mysql = mysqlProcessBuilder.start();
                int exitCode = mysql.waitFor();

                if (exitCode == 0) {
                    new RJAlert(Alert.AlertType.INFORMATION, "Restore process succeeded",
                            "Success", ButtonType.OK).show();
                } else {
                    new RJAlert(Alert.AlertType.ERROR, "Restore process failed, try again!",
                            "Restore failed", "Error", ButtonType.OK).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
