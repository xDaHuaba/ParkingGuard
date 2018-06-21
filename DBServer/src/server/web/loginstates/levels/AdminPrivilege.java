package server.web.loginstates.levels;

import server.db.DBConnect;
import server.web.UserLevel;
import server.web.loginstates.AbstractCommands;
import server.web.loginstates.PrivilegeException;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by dahua on 25-May-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class AdminPrivilege extends AbstractCommands {
	private DBConnect dbConnect;

	private int level=1;

	public int getLevel() {
		return level;
	}

	public AdminPrivilege(DBConnect dbConnect) {
		this.dbConnect = dbConnect;
	}

	@Override
	public boolean check(String numberplate){
		return dbConnect.check(numberplate);
	}

	@Override
	public UserLevel login(String username, String password) {
		return dbConnect.login(username, password);
	}



	@Override
	public int addEntry(String firstname, String lastname, Date startDate, Date endDate, String numberplate){
		return dbConnect.addEntry(firstname, lastname, startDate, endDate, numberplate);
	}

	@Override
	public boolean changeEntry(int personid, String firstname, String lastname, Date startDate, Date endDate, String numberplate){
		return dbConnect.changeEntry(personid, firstname, lastname, startDate, endDate, numberplate);
	}

	@Override
	public boolean deleteEntry(int personid) {
		return dbConnect.deleteEntry(personid);
	}

	@Override
	public String[] viewEntries() {
		return dbConnect.viewEntries();
	}
}
