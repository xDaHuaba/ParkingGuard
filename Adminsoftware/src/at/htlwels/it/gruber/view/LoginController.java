package at.htlwels.it.gruber.view;

import at.htlwels.it.gruber.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    @FXML
    private TextField databaseIPField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;

    private Main main;

    private String ip;
    private String username;
    private String password;

    public final static String filename = "properties.ini";

    public String encryptStringMD5(String s) throws NoSuchAlgorithmException {
        MessageDigest msg = MessageDigest.getInstance("MD5");
        msg.update(s.getBytes());
        return byteToHexInString(msg.digest());
    }

    private String encryptPasswordMD5() throws NoSuchAlgorithmException {
        MessageDigest msg = MessageDigest.getInstance("MD5");
        msg.update(passwordField.getText().getBytes());
        return byteToHexInString(msg.digest());
    }

    private String byteToHexInString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    @FXML
    public void handleConfirm() throws IOException, NoSuchAlgorithmException {
        ip = databaseIPField.getText();
        username = usernameField.getText();
        password = encryptPasswordMD5();

        saveProperties(ip, username, password);

        main.connectToDatabase(ip, username, password);

//        System.out.println(ip + " " + username + " " + password);

        dialogStage.close();
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    private void saveProperties(String ip, String username, String password) throws IOException{
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename))){
            bw.write(ip);bw.newLine();
            bw.write(username);bw.newLine();
            bw.write(password);bw.newLine();
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
