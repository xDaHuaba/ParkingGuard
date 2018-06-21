package server.web;

import com.sun.security.ntlm.Server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by dahua on 20-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class PortListener {
	private int port=41231;
	private String debugheader="[PortListener] ";
	private boolean serverRunning = true;

	private ExecutorService executorService = Executors.newFixedThreadPool(50);

	/**
	 * Start Server
	 *
	 * @return returns -1 if starting the server was unsuccessful
	 */
	public int serverloop() {

		//Creating SLL Server socket
		try (ServerSocket serverSocket = new ServerSocket(port)){

			System.out.println(debugheader+"Starting Server on "+serverSocket.getLocalSocketAddress());

			//While server is running
			while(serverRunning) {
				System.out.println(debugheader+"Waiting for new client connection");
				try {

					//Accepting new Client
					executorService.execute(new ClientHandler(serverSocket.accept()));

				} catch(IOException ioe) {	//Error while accepting remote socket
					System.out.println(debugheader+"[Error] accepting remote connection failed");
					ioe.printStackTrace();
				}
			}
			return 1;
		}catch(IOException e) {	//Error at opening server on port
			System.out.println(debugheader+"[Error] Starting Server on port "+port+" failed");
			e.printStackTrace();
			return -1;
		}
	}
}
