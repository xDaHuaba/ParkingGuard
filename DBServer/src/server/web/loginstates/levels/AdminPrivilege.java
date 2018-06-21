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

	public AdminPrivilege(DBConnect dbConnect) {
		this.dbConnect = dbConnect;
	}

	@Override
	public boolean check(String numberplate) throws SQLException {
		return dbConnect.check(numberplate);
	}

	@Override
	public UserLevel login(String username, String password) throws SQLException {
		return dbConnect.login(username, password);
	}



	@Override
	public int addEntry(String firstname, String lastname, String phonenumber, Date startDate, Date endDate, String numberplate) throws SQLException {
		return dbConnect.addEntry(firstname, lastname, phonenumber, startDate, endDate, numberplate);
	}

	@Override
	public boolean changeEntry(int personid, String firstname, String lastname, String phonenumber, Date startDate, Date endDate, String numberplate) throws SQLException {
		return dbConnect.changeEntry(personid, firstname, lastname, phonenumber, startDate, endDate, numberplate);
	}

	@Override
	public boolean deleteEntry(int personid) throws SQLException {
		return dbConnect.deleteEntry(personid);
	}

	@Override
	public String[] viewEntries() throws PrivilegeException, SQLException {
		return super.viewEntries();
	}
}
