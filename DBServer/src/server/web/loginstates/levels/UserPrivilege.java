package server.web.loginstates.levels;

import server.db.DBConnect;
import server.web.UserLevel;
import server.web.loginstates.AbstractCommands;
import server.web.loginstates.PrivilegeException;

import java.sql.SQLException;

/**
 * Created by dahua on 25-May-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class UserPrivilege extends AbstractCommands {
	private DBConnect dbConnect;

	public UserPrivilege(DBConnect dbConnect) {
		this.dbConnect = dbConnect;
	}

	@Override
	public boolean check(String numberplate) throws SQLException {
		return dbConnect.check(numberplate);
	}

	@Override
	public UserLevel login(String username, String password) throws PrivilegeException {
		try {
			return dbConnect.login(username, password);
		} catch (SQLException e) {
			return null;
		}
	}
}
