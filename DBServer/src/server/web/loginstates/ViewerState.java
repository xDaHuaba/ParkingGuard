package server.web.loginstates;

import server.web.UserLevel;

/**
 * Created by dahua on 27-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class ViewerState extends AbstractCommands{
	@Override
	public boolean check(String numberplate) throws PrivilegeException {
		return super.check(numberplate);
	}

	@Override
	public UserLevel login(String username, String password) throws PrivilegeException {
		return super.login(username, password);
	}
}
