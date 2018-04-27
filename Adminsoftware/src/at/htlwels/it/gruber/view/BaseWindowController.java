package at.htlwels.it.gruber.view;

import at.htlwels.it.gruber.Main;
import at.htlwels.it.gruber.model.DataBaseEntry;
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

public class BaseWindowController {

    @FXML
    private TableView<DataBaseEntry> totalCarsTable;
    @FXML
    private TableColumn<DataBaseEntry, String> licenseCol;
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
//    private static int id = 0;

    public void init(){
        totalCarsTable.setItems(tabledata);
        licenseCol.setCellValueFactory(new PropertyValueFactory<>("license"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    @FXML
    public void connectToNewDatabase() throws IOException{
        main.showDatabaseLoginDialog();
    }

    @FXML
    public void addContent() throws IOException{
//         tabledata.add(new DataBaseEntry(String.valueOf(id++), "Gruber", "Alexander", null, null));
        boolean exitLoop = false;
        DataBaseEntry entry = new DataBaseEntry();
        do {
            NewEditController.cancelled = false;
            showNewEditEntryDialog(entry);
            if (!tabledata.contains(entry)) {
                if(NewEditController.cancelled) exitLoop = true;
                if(!exitLoop) {
                    tabledata.add(entry);
                    totalCarsTable.refresh();
                    exitLoop = true;
                }
            } else if(NewEditController.cancelled) exitLoop = true;
        }while (!exitLoop);
    }

    @FXML
    public void editContent() throws IOException{
        boolean found = false;
        String originalLicense;
        DataBaseEntry selected = totalCarsTable.getSelectionModel().getSelectedItem();
        if(selected!=null) {
            originalLicense = selected.getLicense();
            do {
                showNewEditEntryDialog(selected);
                if (!tabledata.contains(selected)||originalLicense.equals(selected.getLicense())){
                    totalCarsTable.refresh();
                    found = true;
                } else selected.setLicense(originalLicense);
            }while(!found);
        }
    }

    private void showNewEditEntryDialog(DataBaseEntry entry) throws IOException{
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

        dialogStage.showAndWait();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
