package server;

import server.web.PortListener;

import java.net.InetAddress;

/**
 * Created by DaHuaba on 13-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class Main {
	public static void main(String[] args) {
		PortListener portListener=new PortListener();
		portListener.serverloop();
	}
}
