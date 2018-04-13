package at.htlwels.it.gruber.view;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField databaseIPField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;

    private String ip;
    private String username;
    private String password;

    @FXML
    public void handleConfirm(){
        ip = databaseIPField.getText();
        username = usernameField.getText();
        password = passwordField.getText();

        saveProperties(ip, username, password);

     //   main.connectToDatabase(ip, username, password);

        System.out.println(ip + " " + username + " " + password);

        dialogStage.close();
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    public void saveProperties(String ip, String username, String password) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("properties.ini"))){

            bw.write(ip);
            bw.write(username);
            bw.write(password);
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
