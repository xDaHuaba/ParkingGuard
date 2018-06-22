package server.web;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
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
	ComboPooledDataSource cpds = new ComboPooledDataSource();

	/**
	 * Start Server
	 *
	 * @return returns -1 if starting the server was unsuccessful
	 */
	public int serverloop() {

		//Creating SLL Server socket
		try (ServerSocket serverSocket = new ServerSocket(port)){

			System.out.println(debugheader+"Starting Server on "+serverSocket.getLocalSocketAddress());
			initJDBC();


			//While server is running
			while(serverRunning) {
				System.out.println(debugheader+"Waiting for new client connection");
				try {

					//Accepting new Client
					executorService.execute(new ClientHandler(serverSocket.accept(), cpds.getConnection()));

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
		} catch (SQLException e) {
			System.out.println(debugheader+"[Error] SQL Connection failed");
			e.printStackTrace();
			return -1;
		}
	}

	private void initJDBC(){
		try {
			cpds.setDriverClass( "org.postgresql.Driver" );
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/ParkingGuard" );
		cpds.setUser("superuser");
		cpds.setPassword("PC#sql8");

		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
	}

}
