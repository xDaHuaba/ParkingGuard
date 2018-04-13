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

import java.io.IOException;

public class Main extends Application{
    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Verwaltung");

//        initRootLayout();
        showBaseWindowDialog();
    }

//    private void initRootLayout() throws IOException {
//        rootLayout = new AnchorPane();
//
//        primaryStage.setScene(new Scene(rootLayout));
//        primaryStage.show();
//    }

    private void showBaseWindowDialog() throws IOException {
        System.out.println("Set loader location.");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/BaseWindow.fxml"));

        System.out.println("Load FXML file.");
        BorderPane baseWindow = loader.load();
        System.out.println("Done.");

        System.out.println("Set scene.");
        primaryStage.setScene(new Scene(baseWindow));
        System.out.println("Done.");
        primaryStage.show();

        BaseWindowController controller = loader.getController();
        controller.setMain(this);
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
        controller.setDialogStage(dialogStage);

        dialogStage.showAndWait();
//        return controller.isOk_clicked();
    }
}
