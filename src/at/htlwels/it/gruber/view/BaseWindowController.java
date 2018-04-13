package at.htlwels.it.gruber.view;

import at.htlwels.it.gruber.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class BaseWindowController {

    @FXML
    private TableView totalCarsTable;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField databaseIP;

    private Main main;

    @FXML
    public void connectToNewDatabase() throws IOException{
        main.showDatabaseLoginDialog();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
