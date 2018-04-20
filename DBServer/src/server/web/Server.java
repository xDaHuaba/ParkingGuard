package server.web;

import java.net.Socket;

/**
 * Created by dahua on 20-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class Server implements Runnable {
	private Socket clientSocket;

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}


	@Override
	public void run() {

	}
}
