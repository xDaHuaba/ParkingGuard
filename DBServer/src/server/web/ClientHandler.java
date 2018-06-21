package server.web;

import server.db.DBConnect;
import server.web.loginstates.AbstractCommands;
import server.web.loginstates.PrivilegeException;
import server.web.loginstates.levels.AdminPrivilege;
import server.web.loginstates.levels.UserPrivilege;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by dahua on 20-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class ClientHandler implements Runnable {
	private Socket socket;
	private String debugheader;
	private Connection connection;
	private DBConnect dbConnect;

	private boolean clientRunning=true;
	private AbstractCommands userPrivilege;

	public ClientHandler(Socket socket, Connection connection) throws SQLException {
		this.socket = socket;
		this.connection = connection;
		this.dbConnect = new DBConnect(this.connection);
		userPrivilege = new UserPrivilege(this.dbConnect);
	}

	public void run() {
		debugheader = "[ClientHandler "+Thread.currentThread().getId()+" "+socket.getInetAddress()+"] ";
		System.out.println(debugheader+"connected");

		try (PrintWriter out = new PrintWriter(socket.getOutputStream());
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String cmd;

			while (clientRunning){
				System.out.println(debugheader+"Waiting for cmd");
				cmd = in.readLine();
				System.out.println(debugheader+"Client:\t"+cmd);

				if (cmd!=null) handleCmd(cmd, out);
				else {
					System.out.println(debugheader+"Client disconnected");
					clientRunning=false;
				}
			}

		} catch (IOException e) {
			System.out.println(debugheader+"[Error] opening in/outputstream failed");
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException ioe) {
				System.out.println(debugheader+"[Error] closing client connection failed");
			}
		}
	}

	private void handleCmd(String cmd, PrintWriter out){
		try {
			String[] elems = cmd.split(" ");
			if (elems[0].toLowerCase().equals("login")){
				if (elems.length==3) {
					UserLevel up=userPrivilege.login(elems[1],elems[2]);
					if (up==UserLevel.Admin){
						System.out.println(debugheader+"Admin logged in");
						this.userPrivilege=new AdminPrivilege(this.dbConnect);
					}

					if (userPrivilege.getLevel()>0) out.println("ok");
					else out.print("ned ok");
				} else out.print("a ned ok");

			} else if (elems[0].toLowerCase().equals("check")){
				if (elems.length==2) {
					boolean check = userPrivilege.check(elems[1]);
					System.out.println(debugheader+"Numberplate "+elems[1]+" allowed: "+check);
					out.println(check);

				} else out.println(false);

			} else if (elems[0].toLowerCase().equals("addentry")){
				if (elems.length==6) {
					int personID = userPrivilege.addEntry(elems[1],elems[2],Date.valueOf(elems[3]),Date.valueOf(elems[4]),elems[5]);
					System.out.println(debugheader+"Added entry with id "+personID);
					out.println(personID);

				} else out.println(-1);

			} else if (elems[0].toLowerCase().equals("changeentry")){
				if (elems.length==7) {
					boolean worked = userPrivilege.changeEntry(Integer.valueOf(elems[1]), elems[2],elems[3],Date.valueOf(elems[4]),Date.valueOf(elems[5]),elems[6]);
					System.out.println(debugheader+"Entry change successful: "+worked);
					out.println(worked);


				} else out.println("a ned ok");

			} else if (elems[0].toLowerCase().equals("deleteentry")){
				if (elems.length==2) {
					boolean worked = userPrivilege.deleteEntry(Integer.valueOf(elems[1]));
					System.out.println(debugheader+"Deletion of entry successful: "+worked);
					out.println(worked);
				} else out.println(false);

			} else if (elems[0].toLowerCase().equals("viewentries")){
				if (elems.length==1) {
					String[] entries = userPrivilege.viewEntries();
					System.out.println(debugheader+"Returning "+entries.length+" rows");
					out.println(entries.length);
					for (String entry: entries) {
						out.println(entry);
					}
				} else out.println(0);

			}
			out.flush();
		} catch (PrivilegeException e) {
			e.printStackTrace();
			System.out.println("User does not have the permission to do that.");
		}
	}
}

