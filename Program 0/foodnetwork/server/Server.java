/**********************************
 * Author:		John Daniel
 * Assignment:	Program 6
 * Class:		CSI 4321
 **********************************/
package foodnetwork.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import tefub.server.TeFubServer;


/**
 * FoodNetworkServer class - Server for FoodNetwork. Creates both a 
 * 	TCP and UDP connection via the FoodNetwork server 
 * 	and the TeFub server, respectively.
 * @author John Daniel
 *
 */
public class Server {
	
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger("connections.log");
			
		if (args.length != 2){
			String log = "Incorrect argument length: Paramaters: <Port> <# of Threads>";
			logger.log(Level.FINER, log);
			System.exit(1);
		}
		
		int servPort = Integer.parseInt(args[0]);
		int numThreads = Integer.parseInt(args[1]);
		
		// Create a server socket to accept client connection requests
		ServerSocket servTCP = null;
		try {
			servTCP = new ServerSocket(servPort);
			servTCP.setReuseAddress(true);
		} catch (IOException e) {
			String log = "Unable to open socket";
			logger.log(Level.FINER, log);
			System.exit(1);
		}
		// Create TeFub thread to execute alongside the FoodNetwork Server
		ExecutorService serviceUDP = Executors.newFixedThreadPool(1);
		TeFubServer tfs = new TeFubServer(servPort, logger);
		serviceUDP.execute(tfs);
		
		ExecutorService serviceTCP = Executors.newFixedThreadPool(numThreads);
		
		while(true) {
			Socket clntSock = null;
			try {
				clntSock = servTCP.accept();
				clntSock.setSoTimeout(10000);
			} catch (IOException e) {
				String log = "Unable to accept connection";
				logger.log(Level.FINER, log);
			}
			
			serviceTCP.execute(new ServerProtocol(clntSock, logger, tfs));
		}
	 }

}
