package at.htlwels.it.gruber.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class DataBaseEntry {
    private StringProperty license;
//    private int plot_no;
    private StringProperty lastname;
    private StringProperty firstname;
    private LocalDate startDate;
    private LocalDate endDate;

    public DataBaseEntry(String license, /*int plot_no,*/ String lastname, String firstname, LocalDate startDate, LocalDate endDate) {
        this.license = new SimpleStringProperty(license);
//        this.plot_no = plot_no;
        this.lastname = new SimpleStringProperty(lastname);
        this.firstname = new SimpleStringProperty(firstname);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getLicense() {
        return license.get();
    }

    public void setLicense(String license) {
        this.license.set(license);
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
