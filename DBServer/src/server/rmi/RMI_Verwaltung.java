package server.rmi;

/**
 * Created by DaHuaba on 20-Apr-18.
 * Project: ParkingGuardDBServer
 */
public interface RMI_Verwaltung {

	/**
	 * @return returns the entire Table of Entries from Database
	 */
	ListEntry[] getList();

	/**
	 * Changes an existing entry of the Database
	 * @param changedEntry	an ListEntry object with the entry which was changed
	 * @return	returns 1 if changing was successful, -1 if it wasn't
	 */
	int changeEntry(ListEntry changedEntry);

	/**
	 * Adds new entry into the Database
	 * @param newEntry an ListEntry object which is going to be added into the database
	 * @return returns the personID if the inserting was successful, -1 if it wasn't
	 */
	int newEntry(ListEntry newEntry);
}
