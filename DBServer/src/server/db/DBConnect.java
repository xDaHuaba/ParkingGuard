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
	private CallableStatement loginStatement;
	private CallableStatement addEntry;
	private CallableStatement changeEntry;
	private CallableStatement deleteEntry;
	private PreparedStatement viewEntries;
	private PreparedStatement addAdmin;
	private PreparedStatement changeAdmin;
	private PreparedStatement deleteAdmin;


	public DBConnect(Connection connection) throws SQLException {
		this.conn = connection;

		this.checkStatement = conn.prepareCall("{ ? = call parkingguarddb.checknumberplate(?)}");
		this.loginStatement = conn.prepareCall("{ ? = call parkingguarddb.login(?,?)}");
		this.addEntry = conn.prepareCall("{ ? = call parkingguarddb.addentry( ?, ?, ?, ?, ?, ?)}");
		this.changeEntry = conn.prepareCall("{ ? = call parkingguarddb.changeentry( ?, ?, ?, ?, ?, ?, ?)}");
		this.deleteEntry = conn.prepareCall(" { ? = call parkingguarddb.deleteentry(?)}");
		this.viewEntries = conn.prepareStatement("SELECT personid, numberplate, lastname, firstname, startdate, enddate FROM parkingguarddb.person");
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
	public boolean check(String numberplate){
		try {
			checkStatement.registerOutParameter(1, Types.BOOLEAN);
			checkStatement.setString(2, numberplate);
			checkStatement.execute();

			return checkStatement.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * gives the client the userlevel of the user
	 *
	 * @param username username
	 * @param password password
	 * @return Returns the privilege level of the user. Returns -1 if user/password is wrong
	 * @throws SQLException Throws SQLException if something went wrong with database.
	 */
	public UserLevel login(String username, String password){
		try {
			loginStatement.registerOutParameter(1, Types.INTEGER);
			loginStatement.setString(2, username);
			loginStatement.setString(3, password);
//			System.out.println(loginStatement);

			loginStatement.execute();

			int userlevelint = loginStatement.getInt(1);
			return UserLevel.values()[userlevelint];
		} catch (SQLException e) {
			e.printStackTrace();
			return UserLevel.Viewer;
		}
	}

	/**
	 * Adds an Entry to the Database
	 *
	 * @param firstname   First name
	 * @param lastname    Last name
	 * @param startDate   Starting date of numberplate validation
	 * @param endDate     End date of numberplate validation
	 * @param numberplate Numberplate
	 * @return Returns the UserID or -1 if something failed during creation
	 */
	public int addEntry(String firstname, String lastname, Date startDate, Date endDate, String numberplate){
		try {
			addEntry.registerOutParameter(1,Types.INTEGER);
			addEntry.setString(2, firstname);
			addEntry.setString(3, lastname);
			addEntry.setString(4, "");
			addEntry.setDate(5, startDate);
			addEntry.setDate(6, endDate);
			addEntry.setString(7, numberplate);

			addEntry.execute();
			return addEntry.getInt(1);

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
	 * @param startDate   Starting date of numberplate validation
	 * @param endDate     End date of numberplate validation
	 * @param numberplate Numberplate
	 * @return Returns true if update changes were successful
	 */
	public boolean changeEntry(int personid, String firstname, String lastname, Date startDate, Date endDate, String numberplate){
		try {
			changeEntry.registerOutParameter(1,Types.BOOLEAN);
			changeEntry.setInt(2, personid);
			changeEntry.setString(3, firstname);
			changeEntry.setString(4, lastname);
			changeEntry.setString(5, "");
			changeEntry.setDate(6, startDate);
			changeEntry.setDate(7, endDate);
			changeEntry.setString(8, numberplate);
			changeEntry.execute();

			return changeEntry.getBoolean(1);

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
			deleteEntry.registerOutParameter(1,Types.BOOLEAN);
			deleteEntry.setInt(2, personid);
			deleteEntry.execute();

			return deleteEntry.getBoolean(1);

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
	public String[] viewEntries() {
		try {
			ResultSet resultSet = viewEntries.executeQuery();
			LinkedList<String> stringLinkedList = new LinkedList<>();

			//Running through resultSet
			while (resultSet.next()){
				StringBuilder sb = new StringBuilder();
				//Getting colum metadata
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int numberOfColumns = rsmd.getColumnCount();

				//running through columns
				for (int i = 1; i <= numberOfColumns; i++) {
					//Adding cell to the string of the row
					sb.append(resultSet.getString(i));
					//If not last item, add a colon
					if (i < numberOfColumns) {
						sb.append(",");
					}
				}
				//Adding row to list
				stringLinkedList.add(sb.toString());
			}
			//Converting list to array and returning it
			return  stringLinkedList.toArray(new String[stringLinkedList.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
