package server;

import server.web.PortListener;


/**
 * Created by DaHuaba on 13-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class Main {
	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
			PortListener portListener=new PortListener();
			portListener.serverloop();
		} catch(ClassNotFoundException e) {
			System.out.println("is ned do");
		}


	}
}
