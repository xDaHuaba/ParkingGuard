package server.rmi;

import java.time.LocalDate;

/**
 * Created by dahua on 20-Apr-18.
 * Project: ParkingGuardDBServer
 */
public class ListEntry {
	private int personID;
	private String numberPlate;
	private String firstName;
	private String lastName;
	private LocalDate fromDate;
	private LocalDate toDate;

	private ListEntry(String numberPlate, String firstName, String lastName, LocalDate fromDate, LocalDate toDate) {
		this.numberPlate = numberPlate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	private ListEntry(int personID, String numberPlate, String firstName, String lastName, LocalDate fromDate, LocalDate toDate) {
		this.personID = personID;
		this.numberPlate = numberPlate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	private int getPersonID() {
		return personID;
	}

	private void setPersonID(int personID) {
		this.personID = personID;
	}

	private String getNumberPlate() {
		return numberPlate;
	}

	private void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}

	private String getFirstName() {
		return firstName;
	}

	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	private String getLastName() {
		return lastName;
	}

	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private LocalDate getFromDate() {
		return fromDate;
	}

	private void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	private LocalDate getToDate() {
		return toDate;
	}

	private void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
}
