package server.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by dahua on 20-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class ClientHandler implements Runnable {
	private Socket socket;
	private String debugheader;

	private boolean clientRunning=true;
	private UserLevel userPrivilege=UserLevel.Viewer;

	public ClientHandler(Socket connection) {
		this.socket = connection;
	}

	public void run() {
		debugheader = "[ClientHandler"+Thread.currentThread().getId()+"] ";

		try (PrintWriter out = new PrintWriter(socket.getOutputStream());
			 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String cmd;

			while (clientRunning){
				System.out.println(debugheader+"Waiting for cmd");
				cmd = in.readLine();


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
}

