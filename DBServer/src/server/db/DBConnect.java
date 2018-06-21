package server.db;

import server.web.UserLevel;
import server.web.loginstates.AbstractCommands;
import server.web.loginstates.PrivilegeException;
import server.web.loginstates.levels.AdminPrivilege;
import server.web.loginstates.levels.SuperPrivilege;
import server.web.loginstates.levels.UserPrivilege;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by dahua on 13-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class DBConnect implements AutoCloseable {
	private Connection conn;
	private String dbName = "ParkingGuard";

	//		SELECT * FROM person where numberplate like 'AWL5678'

	private CallableStatement checkStatement;
	private PreparedStatement loginStatement;
	private PreparedStatement addEntry;
	private PreparedStatement changeEntry;
	private PreparedStatement deleteEntry;
	private PreparedStatement viewEntries;
	private PreparedStatement addAdmin;
	private PreparedStatement changeAdmin;
	private PreparedStatement deleteAdmin;


	public DBConnect() throws SQLException {
		String url = "jdbc:postgresql://localhost/test";
		Properties props = new Properties();
		props.setProperty("user", "dbAdmin");
		props.setProperty("password", "PC#dbAdmin");
//		props.setProperty("ssl","true");
		this.conn = DriverManager.getConnection(url, props);

		this.checkStatement = conn.prepareCall("{call parkingguarddb.checknumberplate(?)}");
		this.loginStatement = conn.prepareStatement("{call parkingguarddb.login(?,?)}");
		this.addEntry = conn.prepareStatement("{call parkingguarddb.addentry( ?, ?, ?, ?, ?, ?)}");
		this.changeEntry = conn.prepareStatement("{call parkingguarddb.changeentry( ?, ?, ?, ?, ?, ?, ?)}");
		this.deleteEntry = conn.prepareStatement("{call parkingguarddb.deleteentry(?)}");
		this.viewEntries = conn.prepareStatement("SELECT * FROM parkingguarddb.person");
		this.addAdmin = conn.prepareStatement("");
		this.changeAdmin = conn.prepareStatement("");
		this.deleteAdmin = conn.prepareStatement("");


	}

	/**
	 * checks Numberplate in Database
	 *
	 * @param numberplate numberplate to check
	 * @return If numberplate is valid to park return true, else false.
	 * @throws SQLException Throws SQLException if something went wrong with database.
	 */
	public boolean check(String numberplate) throws SQLException {
		checkStatement.setString(1, numberplate);
		ResultSet resultSet = checkStatement.executeQuery();
		return resultSet.first();
	}

	/**
	 * gives the client the userlevel of the user
	 *
	 * @param username username
	 * @param password password
	 * @return Returns the privilege level of the user. Returns -1 if user/password is wrong
	 * @throws SQLException Throws SQLException if something went wrong with database.
	 */
	public UserLevel login(String username, String password) throws SQLException {
		checkStatement.setString(1, username);
		checkStatement.setString(2, password);
		ResultSet resultSet = loginStatement.executeQuery();
		int userlevelint = resultSet.getInt(1);
		return UserLevel.values()[userlevelint];
	}

	/**
	 * Adds an Entry to the Database
	 *
	 * @param firstname   First name
	 * @param lastname    Last name
	 * @param phonenumber phone number
	 * @param startDate   Starting date of numberplate validation
	 * @param endDate     End date of numberplate validation
	 * @param numberplate Numberplate
	 * @return Returns the UserID or -1 if something failed during creation
	 */
	public int addEntry(String firstname, String lastname, String phonenumber, Date startDate, Date endDate, String numberplate){
		try {
			addEntry.setString(1, firstname);
			addEntry.setString(2, lastname);
			addEntry.setString(3, phonenumber);
			addEntry.setDate(4, startDate);
			addEntry.setDate(5, endDate);
			addEntry.setString(6, numberplate);
			ResultSet resultSet = addEntry.executeQuery();
			return resultSet.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				System.err.print("Transaction is being rolled back");
				conn.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}

		}
		return -1;
	}

	/**
	 * Updates changed data from an entry in the Database
	 *
	 * @param personid    The identifier for entry, which is going to the updated
	 * @param firstname   First name
	 * @param lastname    Last name
	 * @param phonenumber phone number
	 * @param startDate   Starting date of numberplate validation
	 * @param endDate     End date of numberplate validation
	 * @param numberplate Numberplate
	 * @return Returns true if update changes were successful
	 */
	public boolean changeEntry(int personid, String firstname, String lastname, String phonenumber, Date startDate, Date endDate, String numberplate){
		try {
			addEntry.setInt(1, personid);
			addEntry.setString(2, firstname);
			addEntry.setString(3, lastname);
			addEntry.setString(4, phonenumber);
			addEntry.setDate(5, startDate);
			addEntry.setDate(6, endDate);
			addEntry.setString(7, numberplate);
			ResultSet resultSet = addEntry.executeQuery();
			return resultSet.getBoolean(1);

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				conn.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * Deletes entire entry
	 *
	 * @param personid The identifier for entry, which is going to the deleted
	 * @return Returns true if deletion was successful
	 */
	public boolean deleteEntry(int personid){
		try {
			addEntry.setInt(1, personid);
			ResultSet resultSet = addEntry.executeQuery();
			return resultSet.getBoolean(1);

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				System.err.print("Transaction is being rolled back");
				conn.rollback();
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		}

		return false;
	}

	/**
	 *
	 * @return
	 */
	public String[] viewEntries() throws SQLException {
		ResultSet resultSet = viewEntries.executeQuery();
		LinkedList<String> stringLinkedList = new LinkedList<>();
		while (resultSet.next()){



		}
	}

	public boolean addAdmin(String username, String password) throws SQLException {
		//TODO addAdmin
		return false;
	}

	public boolean changeAdmin(int userid, String username, String password) throws SQLException {
		//TODO changeAdmin
		return false;
	}

	public boolean deleteAdmin(int userid) throws SQLException {
		//TODO deleteAdmin
		return false;
	}


	@Override
	public void close() throws Exception {
		this.conn.close();
	}
}
