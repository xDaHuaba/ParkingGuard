package server.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by dahua on 20-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class ConServer extends Thread {
	private int portNumber = 13337;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private Server serverThread = new Server();


	@Override
	public void run() {
		super.run();
	}


	private void buildConnectionToClient() {

		try (	ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept()){

			serverThread.setClientSocket(clientSocket);
			executorService.execute(serverThread);

		} catch (IOException e) {
			e.printStackTrace();
		}


	}


}
