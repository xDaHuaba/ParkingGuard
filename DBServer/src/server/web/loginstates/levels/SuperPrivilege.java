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
public class SuperPrivilege extends AbstractCommands {
	private DBConnect dbConnect;

	public SuperPrivilege(DBConnect dbConnect) {
		this.dbConnect = dbConnect;
	}

	@Override
	public boolean check(String numberplate) throws PrivilegeException, SQLException {
		return super.check(numberplate);
	}

	@Override
	public UserLevel login(String username, String password) throws PrivilegeException, SQLException {
		return super.login(username, password);
	}

	@Override
	public int addEntry(String firstname, String lastname, String phonenumber, Date startDate, Date endDate, String numberplate) throws PrivilegeException, SQLException {
		return super.addEntry(firstname, lastname, phonenumber, startDate, endDate, numberplate);
	}

	@Override
	public boolean changeEntry(int personid, String firstname, String lastname, String phonenumber, Date startDate, Date endDate, String numberplate) throws PrivilegeException, SQLException {
		return super.changeEntry(personid, firstname, lastname, phonenumber, startDate, endDate, numberplate);
	}

	@Override
	public boolean deleteEntry(int personid) throws PrivilegeException, SQLException {
		return super.deleteEntry(personid);
	}

	@Override
	public boolean addAdmin(String username, String password) throws PrivilegeException, SQLException {
		return super.addAdmin(username, password);
	}

	@Override
	public boolean changeAdmin(int userid, String username, String password) throws PrivilegeException, SQLException {
		return super.changeAdmin(userid, username, password);
	}

	@Override
	public boolean deleteAdmin(int userid) throws PrivilegeException, SQLException {
		return super.deleteAdmin(userid);
	}
}
