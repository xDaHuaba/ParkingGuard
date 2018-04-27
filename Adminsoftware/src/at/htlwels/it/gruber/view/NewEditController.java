package at.htlwels.it.gruber.view;

import at.htlwels.it.gruber.model.DataBaseEntry;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewEditController {
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField licenseField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private Stage dialogStage;
    private DataBaseEntry entry;
    public static boolean cancelled = false;

    @FXML
    public void saveChanges() {
        if(valid()){
            entry.setLicense(licenseField.getText());
            entry.setFirstname(firstnameField.getText());
            entry.setLastname(lastnameField.getText());
            entry.setStartDate(startDatePicker.getValue());
            entry.setEndDate(endDatePicker.getValue());
            dialogStage.close();
        }
    }

    @FXML
    public void cancel() {
        cancelled = true;
        dialogStage.close();
    }

    private void init() {
        if (entry != null) {
            licenseField.setText(entry.getLicense());
            firstnameField.setText(entry.getFirstname());
            lastnameField.setText(entry.getLastname());
            startDatePicker.setValue(entry.getStartDate());
            endDatePicker.setValue(entry.getEndDate());
        }
    }

    private boolean valid() {
        boolean valid = true;
        if (licenseField.getText().isEmpty()) valid = false;
        else if (firstnameField.getText().isEmpty()) valid = false;
        else if (lastnameField.getText().isEmpty()) valid = false;
//        else if (startDatePicker.getValue() == null) valid = false;
//        else if (endDatePicker.getValue() == null) valid = false;
        return valid;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEntry(DataBaseEntry entry) {
        this.entry = entry;
        init();
    }
}