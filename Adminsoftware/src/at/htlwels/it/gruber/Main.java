package at.htlwels.it.gruber;

import at.htlwels.it.gruber.view.BaseWindowController;
import at.htlwels.it.gruber.view.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application{
    private Stage primaryStage;
    private String ip;
    private String username;
    private String password;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Verwaltung");

        loadProperties();
        connectToDatabase(ip, username, password);
        showBaseWindowDialog();
    }

    private void loadProperties() throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(LoginController.filename))){
            ip = br.readLine();
            username = br.readLine();
            password = br.readLine();
        }
    }

    public void connectToDatabase(String ip, String username, String password) throws IOException{
        //TODO: actually connect to database
        if(ip.isEmpty() || username.isEmpty() || password.isEmpty()) showDatabaseLoginDialog();
        System.out.printf("(pseudo) conncected to %s as %s.\n", ip, username);
    }

    private void showBaseWindowDialog() throws IOException {
        System.out.println("Setting loader location...");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/BaseWindow.fxml"));
        System.out.println("Done.");

        System.out.println("Loading FXML file...");
        BorderPane baseWindow = loader.load();
        System.out.println("Done.");

        System.out.println("Setting scene...");
        primaryStage.setScene(new Scene(baseWindow));
        System.out.println("Done.");
        System.out.println("Starting...");
        primaryStage.show();
        System.out.println("Done.");
        System.out.println("Loading controller.");
        BaseWindowController controller = loader.getController();
        System.out.println("Done.");
        System.out.println("Initializing.");
        controller.setMain(this);
        controller.init();
        System.out.println("Done.");
    }

    public void showDatabaseLoginDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/DatabaseLoginWindow.fxml"));
        AnchorPane databaseLoginDialog = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Neue Datenbank");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setScene(new Scene(databaseLoginDialog));

        LoginController controller = loader.getController();
        controller.setMain(this);
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
//        return controller.isOk_clicked();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
