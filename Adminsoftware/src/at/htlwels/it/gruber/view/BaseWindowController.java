package at.htlwels.it.gruber.view;

import at.htlwels.it.gruber.Main;
import at.htlwels.it.gruber.model.DataBaseEntry;
import com.sun.istack.internal.Nullable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class BaseWindowController {

    @FXML
    private TableView<DataBaseEntry> totalCarsTable;
    @FXML
    private TableColumn<DataBaseEntry, String> licenseCol;
    @FXML
    private TableColumn<DataBaseEntry, String> plotNoCol;
    @FXML
    private TableColumn<DataBaseEntry, String> lastNameCol;
    @FXML
    private TableColumn<DataBaseEntry, String> firstNameCol;
    @FXML
    private TableColumn<DataBaseEntry, String> startDateCol;
    @FXML
    private TableColumn<DataBaseEntry, String> endDateCol;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;

    private Main main;

    private final ObservableList<DataBaseEntry> tabledata = FXCollections.observableArrayList();
    private static int id = 0;

    public void init(){
        totalCarsTable.setItems(tabledata);
//        licenseCol = new TableColumn<>("Kennzeichen");
//        plotNoCol = new TableColumn<>("Nr.");
//        lastNameCol = new TableColumn<>("Nachname");
//        firstNameCol = new TableColumn<>("Vorname");
//        startDateCol = new TableColumn<>("Von");
//        endDateCol = new TableColumn<>("Bis");
        licenseCol.setCellValueFactory(new PropertyValueFactory<>("license"));
//        plotNoCol.setCellValueFactory(new PropertyValueFactory<>("plot_no"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
//        totalCarsTable.getColumns().addAll(licenseCol, plotNoCol, lastNameCol, firstNameCol, startDateCol, endDateCol);
    }

    @FXML
    public void connectToNewDatabase() throws IOException{
        main.showDatabaseLoginDialog();
    }

    @FXML
    public void addContent(){
        tabledata.add(new DataBaseEntry(String.valueOf(id++), "Gruber", "Alexander", null, null));
    }

    @FXML
    public void editContent() throws IOException{
        DataBaseEntry selected = totalCarsTable.getSelectionModel().getSelectedItem();
        if(selected!=null)
            if (showEditEntryDialog(selected)){
//                showSchuelerDetails(selected);
        }
//        else{
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.initOwner(main.getPrimaryStage());
//            alert.setTitle("Nichts ausgewählt");
//            alert.setHeaderText("Kein Schüler ausgewählt");
//            alert.setContentText("Bitte wählen Sie einen Schüler in der Tabelle");
//            alert.showAndWait();
//        }
    }

    private boolean showEditEntryDialog(@Nullable DataBaseEntry entry) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/NewEdit.fxml"));
        AnchorPane databaseLoginDialog = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Bearbeite Eintrag");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(main.getPrimaryStage());
        dialogStage.setScene(new Scene(databaseLoginDialog));

        NewEditController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEntry(entry);
        controller.init();

        dialogStage.showAndWait();

        return controller.valid();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
