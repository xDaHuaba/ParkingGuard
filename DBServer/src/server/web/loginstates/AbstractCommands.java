package server.web.loginstates;

import server.web.UserLevel;

import java.time.LocalDate;

/**
 * Created by dahua on 27-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class AbstractCommands {
	//----- Viewercommands

	public boolean check(String numberplate) throws PrivilegeException {
		throw new PrivilegeException("Not enough permissions to run the command \"check\"");
	}

	public UserLevel login(String username, String password) throws PrivilegeException {
		throw new PrivilegeException("Not enough permissions to run the command \"login\"");
	}

	//----- Admincommands

	public int addEntry(String firstname, String lastname, String phonenumber, LocalDate startDate, LocalDate endDate) throws PrivilegeException  {
		throw new PrivilegeException("Not enough permissions to run the command \"addEntry\"");
	}

	public boolean changeEntry(int personid, String firstname, String lastname, String phonenumber, LocalDate startDate, LocalDate endDate) throws PrivilegeException  {
		throw new PrivilegeException("Not enough permissions to run the command \"changeEntry\"");
	}

	public boolean deleteEntry(int personid) throws PrivilegeException  {
		throw new PrivilegeException("Not enough permissions to run the command \"deleteEntry\"");
	}


	//------- Superusercommands

	public boolean addAdmin(String username, String password) throws PrivilegeException  {
		throw new PrivilegeException("Not enough permissions to run the command \"addAdmin\"");
	}

	public boolean changeAdmin(int userid, String username, String password)  throws PrivilegeException {
		throw new PrivilegeException("Not enough permissions to run the command \"changeAdmin\"");
	}

	public boolean deleteAdmin(int userid) throws PrivilegeException  {
		throw new PrivilegeException("Not enough permissions to run the command \"deleteAdmin\"");
	}
}
